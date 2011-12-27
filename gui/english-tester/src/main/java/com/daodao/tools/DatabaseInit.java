/**
 * 上午10:47:17
 */
package com.daodao.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.access.BootstrapException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhjdenis
 * 
 */
public class DatabaseInit implements BeanFactoryAware
{

    private DataSource dataSource;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org
     * .springframework.beans.factory.BeanFactory)
     */
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        if (!initDB())
        {
            throw new BootstrapException("Error in init database");
        }
    }

    private boolean initDB()
    {
        Connection conn = null;
        try
        {
            conn = dataSource.getConnection();
            String sql = "select * from dictionary";
            conn.createStatement().executeQuery(sql);
        }
        catch (SQLException e)
        {
             e.printStackTrace();
            try
            {

                InputStream input = this.getClass().getClassLoader()
                        .getResourceAsStream("create.sql");
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    conn.createStatement().execute(line);
                }
                return true;
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return false;
            }
            catch (SQLException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return false;
            }

        }

        return true;
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource()
    {
        return dataSource;
    }

    /**
     * @param dataSource
     *            the dataSource to set
     */
    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        while (true)
        {
            try
            {
                Thread.sleep(100000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
