/**
 * 下午6:03:27
 */
package com.daodao;

import org.junit.internal.runners.InitializationError;
import org.powermock.core.spi.PowerMockTestListener;

/**
 * @author zhjdenis
 *
 */
public class DaoDaoJUnit47RunnerDelegateImpl extends DaoDaoJUnit44RunnerDelegateImpl
{

    
    public DaoDaoJUnit47RunnerDelegateImpl(Class<?> klass, String[] methodsToRun, PowerMockTestListener[] listeners)
            throws InitializationError
    {
        super(klass, methodsToRun, listeners);
    }

    public DaoDaoJUnit47RunnerDelegateImpl(Class<?> klass, String[] methodsToRun) throws InitializationError
    {
        super(klass, methodsToRun);
    }

    public DaoDaoJUnit47RunnerDelegateImpl(Class<?> klass) throws InitializationError
    {
        super(klass);
    }


}
