/**
 * 下午5:32:20
 */
package com.daodao;

import java.sql.Connection;

import org.junit.runner.RunWith;

/**
 * @author zhjdenis
 * 
 */
@RunWith(DaoDaoDBTestRunner.class)
@DaoDaoDBConfigLocation(locations = { "dbconfig.properties" })
abstract public class DaoDaoTest {

	protected Connection conn;

}
