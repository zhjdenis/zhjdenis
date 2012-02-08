package com.daodao.example.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.daodao.dao.ConnectionPool;
import com.daodao.dao.Test1DAO;
import com.daodao.dao.Test2DAO;
import com.daodao.framework.BaseTest;
import com.daodao.framework.annotation.DaoDaoDBConnection;
import com.daodao.framework.annotation.DaoDaoDBDataSet;

@PrepareForTest(value = {ConnectionPool.class})
public class DBTest extends BaseTest {

    @DaoDaoDBConnection(connection="local")
    private Connection conn;
    
    private Test1DAO test1DAO;
    private Test2DAO test2DAO;
    
    @Before
    public void init() {
        test1DAO = new Test1DAO();
        test2DAO = new Test2DAO();
    }
    
	@Test
	@DaoDaoDBDataSet(connection = "local", locations = {"ds.xml"})
	public void testInsertDB() throws SQLException {
	    Mockito.when(ConnectionPool.getConnection()).thenReturn(conn);
	    Assert.assertEquals(1, test2DAO.countAll());
	    test2DAO.insertData();
	    Assert.assertEquals(2, test2DAO.countAll());
	}
}