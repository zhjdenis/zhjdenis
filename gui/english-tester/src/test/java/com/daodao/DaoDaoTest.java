/**
 * 下午5:32:20
 */
package com.daodao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author zhjdenis
 * 
 */
@RunWith(DaoDaoDBTestRunner.class)
@DaoDaoDBConfigLocation(locations = { "dbconfig.properties" })
public class DaoDaoTest {

	protected Connection conn;

	@Before
	public void init() throws SQLException {
		conn.setAutoCommit(false);
	}

	@After
	public void destroy() throws SQLException {
		conn.rollback();
	}

	@Test
	@DaoDaoDBConnection("test")
	public void test() throws SQLException {
		String sql = "select count(*) from dictionary";
		ResultSet rs = conn.createStatement().executeQuery(sql);
		if (rs.next()) {
			Assert.assertNotNull(rs.getInt(1));
		} else {
			Assert.fail();
		}
	}

}
