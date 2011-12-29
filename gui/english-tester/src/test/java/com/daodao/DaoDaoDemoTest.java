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
public class DaoDaoDemoTest extends DaoDaoTest
{

   
    @Test
    @DaoDaoDBConnection("zhjut")
    public void test() throws SQLException
    {
        String sql = "select count(*) from t_zhj_ut";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if (rs.next()) {
            Assert.assertNotNull(rs.getInt(1));
        } else {
            Assert.fail();
        }
    }


}
