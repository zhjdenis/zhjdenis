/**
 * 下午2:28:20
 */
package com.daodao;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import com.daodao.service.SingletonPool;
import com.daodao.ut.DaoDaoDBConnection;
import com.daodao.ut.DaoDaoDBDataSet;
import com.daodao.ut.DaoDaoTest;

/**
 * @author zhjdenis
 * 
 */
@DaoDaoDBConnection("test")
public class DaoDaoDemoTest extends DaoDaoTest
{
    

    @Mock
    private SingletonPool pool;

    // @Rule
    // public PowerMockRule rule = new PowerMockRule();

    @Test
    public void testSQL() throws SQLException
    {
        String sql = "select count(*) from dictionary";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if (rs.next())
        {
            System.out.println(rs.getInt(1));
            Assert.assertNotNull(rs.getInt(1));
        }
        else
        {
            Assert.fail();
        }
    }

    @Test
    @DaoDaoDBDataSet(locations = { "demo_ds.xml" })
    public void testSQL2() throws SQLException
    {
        String sql = "select count(*) from dictionary";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if (rs.next())
        {
            System.out.println(rs.getInt(1));
            // Assert.assertEquals(4538, rs.getInt(1));
        }
        else
        {
            Assert.fail();
        }
        sql = "select * from dictionary where en='zeal' or en='zhjdenis'";
        rs = conn.createStatement().executeQuery(sql);
        while (rs.next())
        {
            System.out.println(rs.getString("en"));
            System.out.println(rs.getString("zh"));
        }
    }

    @Test
    public void testSQL3() throws SQLException
    {
        String sql = "select count(*) from dictionary";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if (rs.next())
        {
            System.out.println(rs.getInt(1));
            Assert.assertNotNull(rs.getInt(1));
        }
        else
        {
            Assert.fail();
        }
    }

    @Test
    public void testMockit()
    {
        SingletonPool pool = mock(SingletonPool.class);
        when(pool.getString()).thenReturn("haha");
        Assert.assertEquals(pool.getString(),"haha");
    }

    @Test
    public void testMockStatic()
    {
        PowerMockito.mockStatic(SingletonPool.class);
        when(SingletonPool.getDefaultInt()).thenReturn(3);
        Assert.assertEquals(SingletonPool.getDefaultInt().intValue(), 3);
        when(SingletonPool.getDefaultInt("tt")).thenReturn(2);
        Assert.assertEquals(SingletonPool.getDefaultInt("tt").intValue(), 2);
        when(SingletonPool.getDefaultInt(anyString())).thenReturn(2);
        Assert.assertEquals(SingletonPool.getDefaultInt("oo").intValue(), 2);
    }

    @Test
    public void testMockPrivate()
    {
        // PowerMockito.mockStatic(SingletonPool.class);
        SingletonPool newPool = PowerMockito.spy(SingletonPool.getInstance());
        try
        {
            PowerMockito.doReturn("oo").when(newPool, "getDefaultString");
            System.out.println(newPool.getString());
            PowerMockito.verifyPrivate(newPool, times(1)).invoke("getDefaultString");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Assert.assertNotNull(newPool.getString());
    }

}
