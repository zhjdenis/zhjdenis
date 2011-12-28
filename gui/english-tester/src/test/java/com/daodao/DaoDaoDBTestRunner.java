/**
 * 下午5:27:58
 */
package com.daodao;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;

/**
 * @author zhjdenis
 *
 */
public class DaoDaoDBTestRunner extends JUnit4ClassRunner
{

    /**
     * @param klass
     * @throws InitializationError
     */
    public DaoDaoDBTestRunner(Class<?> klass) throws InitializationError
    {
        super(klass);
    }

}
