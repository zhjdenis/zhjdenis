/**
 * 下午6:03:27
 */
package com.daodao;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
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
import org.powermock.core.spi.PowerMockTestListener;
import org.powermock.modules.junit4.internal.impl.PowerMockJUnit44RunnerDelegateImpl;

/**
 * @author zhjdenis
 * 
 */
public class DaoDaoJUnit44RunnerDelegateImpl extends PowerMockJUnit44RunnerDelegateImpl
{

    protected Map<String, Connection> connections = new HashMap<String, Connection>();

    public DaoDaoJUnit44RunnerDelegateImpl(Class<?> klass, String[] methodsToRun, PowerMockTestListener[] listeners)
            throws InitializationError
    {
        super(klass, methodsToRun, listeners);
        initConfig(klass);
    }

    public DaoDaoJUnit44RunnerDelegateImpl(Class<?> klass, String[] methodsToRun) throws InitializationError
    {
        super(klass, methodsToRun);
        initConfig(klass);
    }

    public DaoDaoJUnit44RunnerDelegateImpl(Class<?> klass) throws InitializationError
    {
        super(klass);
        initConfig(klass);
    }

    @Override
    protected void invokeTestMethod(final Method method, RunNotifier notifier)
    {
        Description description = methodDescription(method);
        final Object testInstance;
        try
        {
            testInstance = createTest();
            MockitoAnnotations.initMocks(testInstance);
            injectConnection(method, testInstance);
            injectData(method, testInstance);
        }
        catch (InvocationTargetException e)
        {
            testAborted(notifier, description, e.getTargetException());
            return;
        }
        catch (Exception e)
        {
            testAborted(notifier, description, e);
            return;
        }

        // Check if we extend from TestClass, in that case we must run the
        // setUp
        // and tearDown methods.
        final boolean extendsFromTestCase = TestCase.class.isAssignableFrom(getTestClass()) ? true : false;

        final TestMethod testMethod = wrapMethod(method);
        createPowerMockRunner(testInstance, testMethod, notifier, description, extendsFromTestCase).run();
    }

    /**
     * print error message
     * 
     * @param notifier
     * @param description
     * @param e
     */
    protected void testAborted(RunNotifier notifier, Description description, Throwable e)
    {
        notifier.fireTestStarted(description);
        notifier.fireTestFailure(new Failure(description, e));
        notifier.fireTestFinished(description);
    }

    protected void injectData(Method method, Object targetObj) throws Exception
    {
        Annotation[] allAnnotations = method.getAnnotations();
        DaoDaoDBDataSet dsAnnotation = null;
        Class targetClass = targetObj.getClass();
        for (Annotation annotation : allAnnotations)
        {
            if (annotation instanceof DaoDaoDBDataSet)
            {
                dsAnnotation = (DaoDaoDBDataSet) annotation;
                break;
            }
        }
        if (dsAnnotation == null)
        {
            return;
        }
        Field connField = lookupField(targetClass, "conn");
        if (connField == null)
        {
            throw new DaoDaoDBTestException(
                    "Your class should contain a field named 'conn' type in java.sql.Connection");
        }
        if (!connField.isAccessible())
        {
            connField.setAccessible(true);
        }
        Connection conn = (Connection) connField.get(targetObj);
        String[] dsLocations = dsAnnotation.locations();
        for (String dsLocation : dsLocations)
        {
            IDataSet ds = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResource(dsLocation));
            dsAnnotation.operation().getOp().execute(new DatabaseConnection(conn), ds);
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
    protected Field lookupField(Class targetClass, String fieldName)
    {
        Field[] fields = targetClass.getDeclaredFields();
        Field connField = null;
        for (Field field : fields)
        {
            if (field.getName().equals(fieldName))
            {
                connField = field;
                return connField;
            }
        }
        // inject connection to class that extends DaoDaoTest.class
        while (connField == null)
        {
            targetClass = targetClass.getSuperclass();
            if (targetClass.equals(Object.class))
            {
                return null;
            }
            fields = targetClass.getDeclaredFields();
            for (Field field : fields)
            {
                if (field.getName().equals(fieldName))
                {
                    connField = field;
                    break;
                }
            }
        }
        return connField;
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
    protected void injectConnection(Method method, Object targetObj) throws SQLException, Exception
    {
        Annotation[] allAnnotations = method.getAnnotations();
        DaoDaoDBConnection dbAnnotation = null;
        Class targetClass = targetObj.getClass();
        for (Annotation annotation : allAnnotations)
        {
            if (annotation instanceof DaoDaoDBConnection)
            {
                dbAnnotation = (DaoDaoDBConnection) annotation;
                break;
            }
        }
        // try to get the default connect from class region
        if (dbAnnotation == null)
        {
            dbAnnotation = (DaoDaoDBConnection) targetClass.getAnnotation(DaoDaoDBConnection.class);
        }
        String conKey = dbAnnotation.value();
        if (!connections.containsKey(conKey) || connections.get(conKey) == null)
        {
            throw new DaoDaoDBTestException("Can not find the configuration for connection name:" + conKey);
        }
        else
        {
            Field connField = lookupField(targetClass, "conn");
            if (connField == null)
            {
                throw new DaoDaoDBTestException(
                        "Your class should contain a field named 'conn' type in java.sql.Connection");
            }
            if (!connField.isAccessible())
            {
                connField.setAccessible(true);
            }
            // erase the previous data
            connections.get(conKey).rollback();
            connField.set(targetObj, connections.get(conKey));
        }
    }

    /**
     * load config
     * 
     * @param configLocations
     */
    protected void initConfig(Class klass)
    {
        
        DaoDaoDBConfigLocation configAnnotation = (DaoDaoDBConfigLocation) klass
                .getAnnotation(DaoDaoDBConfigLocation.class);
        if(configAnnotation == null) {
            return;
        }
        String[] configLoactions = configAnnotation.locations();
        Properties prop = new Properties();
        for (String config : configLoactions)
        {
            try
            {
                prop.load(this.getClass().getClassLoader().getResourceAsStream(config));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                continue;
            }
        }
        for (Entry<Object, Object> entry : prop.entrySet())
        {
            String key = entry.getKey().toString();
            key = key.substring(0, key.indexOf("."));
            if (connections.containsKey(key))
            {
                continue;
            }
            String jdbcDriver = prop.get(key + ".database.driver").toString();
            String server = prop.get(key + ".database.server").toString();
            String username = "";
            String password = "";
            if (prop.containsKey(key + ".database.username"))
            {
                username = prop.get(key + ".database.username").toString();
            }
            if (prop.containsKey(key + ".database.password"))
            {
                password = prop.get(key + ".database.password").toString();
            }
            try
            {
                Class.forName(jdbcDriver);
                Connection conn = DriverManager.getConnection(server, username, password);
                conn.setAutoCommit(false);
                connections.put(key, conn);
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
                connections.put(key, null);
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                connections.put(key, null);
            }
        }
    }

}
