/**
 * 下午5:32:20
 */
package com.daodao.ut;

import java.sql.Connection;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.daodao.service.SingletonPool;

/**
 * @author zhjdenis
 * 
 */
@RunWith(DaoDaoAllTestRunner.class)
//@RunWith(PowerMockRunner.class)
@PrepareForTest(SingletonPool.class)
//@RunWith(DaoDaoDBTestRunner48.class)
//@RunWith(DaoDaoDBTestRunner.class)
@PowerMockIgnore({"javax.management.*","org.xml.*", "javax.xml.*"}) 
@DaoDaoDBConfigLocation(locations = { "dbconfig.properties" })
abstract public class DaoDaoTest {

	protected Connection conn;
	

}
