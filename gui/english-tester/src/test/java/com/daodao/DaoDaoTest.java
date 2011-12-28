/**
 * 下午5:32:20
 */
package com.daodao;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author zhjdenis
 *
 */
@RunWith(DaoDaoDBTestRunner.class)
@DaoDaoDBConfigLocation(locations={"dbconfig.properties"})
public class DaoDaoTest
{

    @Test
    public void test()
    {
        fail("Not yet implemented");
    }

}
