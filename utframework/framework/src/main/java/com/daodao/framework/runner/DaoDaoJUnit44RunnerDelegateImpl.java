package com.daodao.framework.runner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import junit.framework.TestCase;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.spi.PowerMockTestListener;
import org.powermock.modules.junit4.internal.impl.PowerMockJUnit44RunnerDelegateImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daodao.framework.DaoDaoDBTestException;
import com.daodao.framework.annotation.DaoDaoDBConfigLocation;
import com.daodao.framework.annotation.DaoDaoDBConnection;
import com.daodao.framework.annotation.DaoDaoDBDataSet;
import com.daodao.framework.annotation.DaoDaoIgnoreMock;

/**
 * @author zhjdenis
 * 
 */
public class DaoDaoJUnit44RunnerDelegateImpl extends
		PowerMockJUnit44RunnerDelegateImpl {

	private static final Logger LOG = LoggerFactory
			.getLogger(DaoDaoJUnit44RunnerDelegateImpl.class.toString());
	protected Map<String, Connection> connections = new HashMap<String, Connection>();
	protected Class klass;

	public DaoDaoJUnit44RunnerDelegateImpl(Class<?> klass,
			String[] methodsToRun, PowerMockTestListener[] listeners)
			throws InitializationError {
		super(klass, methodsToRun, listeners);
		this.klass = klass;
		initConfig(klass);
	}

	public DaoDaoJUnit44RunnerDelegateImpl(Class<?> klass, String[] methodsToRun)
			throws InitializationError {
		super(klass, methodsToRun);
		this.klass = klass;
		initConfig(klass);
	}

	public DaoDaoJUnit44RunnerDelegateImpl(Class<?> klass)
			throws InitializationError {
		super(klass);
		this.klass = klass;
		initConfig(klass);
	}

	@Override
	protected void invokeTestMethod(final Method method, RunNotifier notifier) {
		Description description = methodDescription(method);
		final Object testInstance;
		try {
			testInstance = createTest();
			MockitoAnnotations.initMocks(testInstance);
			injectConnection(method, testInstance);
			injectData(method, testInstance);
			mockStatic(method);
		} catch (InvocationTargetException e) {
			testAborted(method, notifier, description, e.getTargetException());
			return;
		} catch (Exception e) {
			testAborted(method, notifier, description, e);
			return;
		}

		// Check if we extend from TestClass, in that case we must run the
		// setUp
		// and tearDown methods.
		final boolean extendsFromTestCase = TestCase.class
				.isAssignableFrom(getTestClass()) ? true : false;

		final TestMethod testMethod = wrapMethod(method);
		createPowerMockRunner(testInstance, testMethod, notifier, description,
				extendsFromTestCase).run();
	}

	/**
	 * print error message
	 * 
	 * @param notifier
	 * @param description
	 * @param e
	 */
	protected void testAborted(Method method, RunNotifier notifier,
			Description description, Throwable e) {
		Annotation[] allAnnotations = method.getAnnotations();
		for (Annotation annotation : allAnnotations) {
		}
		Failure failure = new Failure(description, e);
		LOG.error("Test Failed: " + failure.getTestHeader());
		LOG.error("Failure Message: " + failure.getMessage());
		notifier.fireTestStarted(description);
		notifier.fireTestFailure(new Failure(description, e));
		notifier.fireTestFinished(description);
	}

	/**
	 * @param method
	 * @param targetObj
	 * @throws Exception
	 */
	protected void injectData(Method method, Object targetObj) throws Exception {
		Annotation[] allAnnotations = method.getAnnotations();
		DaoDaoDBDataSet dsAnnotation = null;
		for (Annotation annotation : allAnnotations) {
			if (annotation instanceof DaoDaoDBDataSet) {
				dsAnnotation = (DaoDaoDBDataSet) annotation;
				break;
			}
		}
		if (dsAnnotation == null) {
			return;
		}
		String connKey = dsAnnotation.connection();
		Connection conn = connections.get(connKey);
		String[] dsLocations = dsAnnotation.locations();
		for (String dsLocation : dsLocations) {
			IDataSet ds = new FlatXmlDataSetBuilder().build(this.getClass()
					.getClassLoader().getResource(dsLocation));
			dsAnnotation.operation().getOp()
					.execute(new DatabaseConnection(conn), ds);
		}
	}

	/**
	 * find the declare field(include heritage field) in object by field name,
	 * 
	 * @param targetObj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	protected List<Field> lookupField(Class targetClass, Class fieldType) {
		if (targetClass.equals(Object.class)) {
			return Collections.EMPTY_LIST;
		}
		Field[] fields = targetClass.getDeclaredFields();
		List<Field> rs = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(fieldType)) {
				rs.add(field);
			}
		}
		rs.addAll(lookupField(targetClass.getSuperclass(), fieldType));
		return rs;
	}

	protected void mockStatic(Method method) {
		DaoDaoIgnoreMock ignoreAnnotation = (DaoDaoIgnoreMock) method
				.getAnnotation(DaoDaoIgnoreMock.class);
		PrepareForTest prepareAnnotation = (PrepareForTest) klass
				.getAnnotation(PrepareForTest.class);
		if (prepareAnnotation == null) {
			return;
		}
		List<Class> ignoreClass = new ArrayList<Class>();
		if (ignoreAnnotation != null) {
			ignoreClass = Arrays.asList(ignoreAnnotation.value());
		}
		Class[] candidateClass = prepareAnnotation.value();
		for (Class klass : candidateClass) {
			if (!ignoreClass.contains(klass)) {
				PowerMockito.mockStatic(klass);
			}
		}
	}

	/**
	 * inject connection from {@link DaoDaoDBConnection}
	 * 
	 * @param method
	 * @param notifier
	 * @param targetObj
	 * @throws SQLException
	 * @throws Exception
	 */
	protected void injectConnection(Method method, Object targetObj)
			throws SQLException, Exception {
	        Class targetClass = targetObj.getClass();
	        Annotation defaultAnnotation = targetClass.getAnnotation(DaoDaoDBConnection.class);
			List<Field> connFields = lookupField(targetClass, Connection.class);
			for (Field connField : connFields) {
				DaoDaoDBConnection specificAnnotation = connField
						.getAnnotation(DaoDaoDBConnection.class);
				if (!connField.isAccessible()) {
				    connField.setAccessible(true);
				}
				if (specificAnnotation != null) {
				    if(! connections.containsKey(specificAnnotation.connection())) {
				        throw new Exception("You should define a connection with key:"+specificAnnotation.connection());
				    }
				    connections.get(specificAnnotation.connection()).rollback();
					connField.set(targetObj,
							connections.get(specificAnnotation.connection()));
				} else if(defaultAnnotation != null) {
				    if(! connections.containsKey(((DaoDaoDBConnection)defaultAnnotation).connection())) {
				        throw new Exception("You should define a connection with key:"+((DaoDaoDBConnection)defaultAnnotation).connection());
				    }
				    connections.get(((DaoDaoDBConnection)defaultAnnotation).connection()).rollback();
				    connField.set(targetObj,
				                  connections.get(((DaoDaoDBConnection)defaultAnnotation).connection()));
				}
			}
	}

	/**
	 * load config
	 * 
	 * @param configLocations
	 */
	protected void initConfig(Class klass) {

		DaoDaoDBConfigLocation configAnnotation = (DaoDaoDBConfigLocation) klass
				.getAnnotation(DaoDaoDBConfigLocation.class);
		if (configAnnotation == null) {
			return;
		}
		String[] configLoactions = configAnnotation.locations();
		Properties prop = new Properties();
		for (String config : configLoactions) {
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
			if (prop.containsKey(key + ".database.username")) {
				username = prop.get(key + ".database.username").toString();
			}
			if (prop.containsKey(key + ".database.password")) {
				password = prop.get(key + ".database.password").toString();
			}
			try {
				Class.forName(jdbcDriver);
				Connection conn = DriverManager.getConnection(server, username,
						password);
				conn.setAutoCommit(false);
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

}
