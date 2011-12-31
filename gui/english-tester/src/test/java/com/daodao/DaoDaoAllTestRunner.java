/**
 * 下午4:52:44
 */
package com.daodao;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.Map;

import junit.runner.Version;

import org.powermock.core.spi.PowerMockTestListener;
import org.powermock.modules.junit4.common.internal.impl.AbstractCommonPowerMockRunner;

/**
 * @author zhjdenis
 * 
 */
public class DaoDaoAllTestRunner extends AbstractCommonPowerMockRunner
{

    protected Map<String, Connection> connections;

    /**
     * @param klass
     * @throws Exception
     */
    public DaoDaoAllTestRunner(Class<?> klass) throws Exception
    {
        super(klass, getJUnitVersion() >= 4.7f ? DaoDaoJUnit47RunnerDelegateImpl.class
                : DaoDaoJUnit44RunnerDelegateImpl.class);
    }

    private static float getJUnitVersion()
    {
        String version = Version.id();
        int dot = version.indexOf('.');
        // Fix bug when the junit version above 4.9
        if (version.startsWith("4.10"))
        {
            return 4.9f;
        }
        if (dot > 0)
        {
            // Make sure that only one dot exists
            dot = version.indexOf('.', dot + 1);
            if (dot > 0)
            {
                /*
                 * If minor version such as 4.8.1 then remove the last digit,
                 * e.g. "4.8.1" becomes "4.8".
                 */
                version = version.substring(0, dot);
            }
        }
        try
        {
            return Float.parseFloat(version);
        }
        catch (NumberFormatException e)
        {
            // If this happens we revert to JUnit 4.4 runner
            return 4.4f;
        }
    }

   

   
    

    public static void main(String[] args)
    {
        try
        {
            final Class<?> powerMockTestListenerArrayType = Class.forName(PowerMockTestListener[].class.getName());
            final Class<?> delegateClass = Class.forName(DaoDaoJUnit47RunnerDelegateImpl.class.getName());
            Constructor<?> con = delegateClass.getConstructor(new Class[] {
                    Class.class,
                    String[].class,
                    powerMockTestListenerArrayType });
            System.out.println(con == null);
        }
        catch (SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
