/**
 * 下午2:28:20
 */
package com.daodao;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author zhjdenis
 * 
 */
@DaoDaoDBConnection("test")
public class DaoDaoDemoTest extends DaoDaoTest
{

   
    @Test
    public void testSQL() throws SQLException
    {
        String sql = "select count(*) from dictionary";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if (rs.next()) {
            System.out.println(rs.getInt(1));
            Assert.assertNotNull(rs.getInt(1));
        } else {
            Assert.fail();
        }
    }
    
    @Test
    @DaoDaoDBDataSet(locations={"demo_ds.xml"})
    public void testSQL2() throws SQLException {
        String sql = "select count(*) from dictionary";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if (rs.next()) {
            System.out.println(rs.getInt(1));
//            Assert.assertEquals(4538, rs.getInt(1));
        } else {
            Assert.fail();
        }
        sql = "select * from dictionary where en='zeal' or en='zhjdenis'";
        rs = conn.createStatement().executeQuery(sql);
        while(rs.next()) {
            System.out.println(rs.getString("en"));
            System.out.println(rs.getString("zh"));
        }
    }
    @Test
    public void testSQL3() throws SQLException
    {
        String sql = "select count(*) from dictionary";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if (rs.next()) {
            System.out.println(rs.getInt(1));
            Assert.assertNotNull(rs.getInt(1));
        } else {
            Assert.fail();
        }
    }


}
