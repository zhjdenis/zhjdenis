/**
 * 下午5:27:58
 */
package com.daodao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * @author zhjdenis
 * 
 */
public class DaoDaoDBTestRunner extends JUnit4ClassRunner {

	protected Map<String, Connection> connections;

	/**
	 * @param klass
	 * @throws InitializationError
	 */
	public DaoDaoDBTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
		connections = new HashMap<String, Connection>();
		DaoDaoDBConfigLocation config = klass
				.getAnnotation(DaoDaoDBConfigLocation.class);
		if (config != null) {
			initConfig(config.locations());
		}
	}

	@Override
	protected void invokeTestMethod(Method method, RunNotifier notifier) {
		DaoDaoDBConnection dbconnection = method
				.getAnnotation(DaoDaoDBConnection.class);
		String conKey = dbconnection.value();
		if (!connections.containsKey(conKey) || connections.get(conKey) == null) {
			Description description = methodDescription(method);
			testAborted(notifier, description, new DaoDaoDBTestException(
					"Empty"));
		} else {

			Description description = methodDescription(method);
			Object test;
			try {
				test = createTest();
				getTestClass().getJavaClass().getDeclaredField("conn")
						.set(test, connections.get(conKey));
			} catch (InvocationTargetException e) {
				testAborted(notifier, description, e.getCause());
				return;
			} catch (Exception e) {
				testAborted(notifier, description, e);
				return;
			}
			TestMethod testMethod = wrapMethod(method);
			new MethodRoadie(test, testMethod, notifier, description).run();

		}
	}

	private void testAborted(RunNotifier notifier, Description description,
			Throwable e) {
		notifier.fireTestStarted(description);
		notifier.fireTestFailure(new Failure(description, e));
		notifier.fireTestFinished(description);
	}

	/**
	 * load config
	 * 
	 * @param configLocations
	 */
	protected void initConfig(String[] configLocations) {
		Properties prop = new Properties();
		for (String config : configLocations) {
			try {
				prop.load(this.getClass().getClassLoader()
						.getResourceAsStream(config));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		for (Entry<Object, Object> entry : prop.entrySet()) {
			String key = entry.getKey().toString();
			key = key.substring(0, key.indexOf("."));
			if (connections.containsKey(key)) {
				continue;
			}
			String jdbcDriver = prop.get(key + ".database.driver").toString();
			String server = prop.get(key + ".database.server").toString();
			String username = "";
			String password = "";
			if (prop.contains(key + ".database.username")) {
				username = prop.get(key + ".database.username").toString();
			}
			if (prop.contains(key + ".database.password")) {
				password = prop.get(key + ".database.password").toString();
			}
			try {
				Class.forName(jdbcDriver);
				Connection conn = DriverManager.getConnection(server, username,
						password);
				connections.put(key, conn);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				connections.put(key, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				connections.put(key, null);
			}
		}
	}

	public static void main(String[] args) {
		try {
			new DaoDaoDBTestRunner(DaoDaoTest.class);
		} catch (InitializationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
