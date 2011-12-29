/**
 * 下午5:32:20
 */
package com.daodao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author zhjdenis
 * 
 */
@RunWith(DaoDaoDBTestRunner.class)
@DaoDaoDBConfigLocation(locations = { "dbconfig.properties" })
abstract public class DaoDaoTest {

	protected Connection conn;
	
	@Before
	protected void init() throws SQLException {
	    conn.setAutoCommit(false);
	}
	
	@After
	protected void destory() throws SQLException {
	    conn.rollback();
	}

}
