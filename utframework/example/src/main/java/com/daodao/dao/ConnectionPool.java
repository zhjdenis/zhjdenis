/**
 * ÉÏÎç11:45:22
 */
package com.daodao.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * @author zhjdenis
 *
 */
public class ConnectionPool
{

    private static BoneCP connectionPool;
    static {
        try
        {
            Class.forName("org.hsqldb.jdbcDriver");
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl("jdbc:hsqldb:mem:test"); 
            config.setUsername("sa"); 
            config.setPassword("");
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            connectionPool = new BoneCP(config);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    public static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }
    public static void main(String[] args) throws SQLException
    {
        getConnection();
    }
}
