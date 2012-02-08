/**
 * ÉÏÎç11:39:06
 */
package com.daodao.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zhjdenis
 *
 */
public class Test1DAO
{

    
    public int countAll() throws SQLException {
        ResultSet rs = ConnectionPool.getConnection().createStatement().executeQuery("select count(*) as total from table2");
        while(rs.next()) {
            return rs.getInt("total");
        }
        return 0;
    }
    
    public void insertData() throws SQLException {
        ConnectionPool.getConnection().createStatement().execute("insert into table1 (value) values ('test')");
    }
    
    public int deleteData() {
        return 0;
    }
    
    public int updateData() {
        return 0;
    }
    
    public List<Object> list() {
        return null;
    }
}
