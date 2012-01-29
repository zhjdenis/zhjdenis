package com.daodao.framework;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;

import com.daodao.framework.annotation.DaoDaoDBConfigLocation;
import com.daodao.framework.runner.DaoDaoAllTestRunner;


/**
 * @author zhjdenis
 * 
 */
@RunWith(DaoDaoAllTestRunner.class)
@PowerMockIgnore({"javax.management.*","org.xml.*", "javax.xml.*"}) 
@DaoDaoDBConfigLocation(locations = { "dbconfig.properties" })
abstract public class BaseTest {

}
