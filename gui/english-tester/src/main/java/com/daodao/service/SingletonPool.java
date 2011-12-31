package com.daodao.service;

import java.sql.Connection;

/**
 * 上午10:51:17
 */

/**
 * @author zhjdenis
 * 
 */
public class SingletonPool
{

    private static SingletonPool instance;

    private SingletonPool()
    {

    }

    public Connection getConnection()
    {
        return null;
    }

    public static SingletonPool getInstance()
    {
        if (instance == null)
        {
            instance = new SingletonPool();
        }
        return instance;
    }

    public static Connection getDefaultConnection()
    {
        getInstance();
        return instance.getConnection();
    }

    public static Integer getDefaultInt()
    {
        return null;
    }

    public static Integer getDefaultInt(String str)
    {
        return null;
    }

    private String getDefaultString()
    {
        return null;
    }

    public String getString()
    {
        return getDefaultString();
    }

    public String getString(String str)
    {
        return getDefaultString();
    }
}
