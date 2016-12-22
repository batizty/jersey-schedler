package com.weibo.datasys.base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by tuoyu on 22/12/2016.
 */
public class MySqlConnector implements ServletContextListener {
//    protected final Log logger = LogFactory.getLog(getClass());
    public static Connection connection;


    private final String host = "10.77.136.64";
    private final String port = "3306";
    private final String username = "hadoop";
    private final String password = "hadoop";
    private final String database = "datasys_monitor";
    private final String sql_url = "jdbc:mysql://" + host + ":" + port + "/" + database;
    private final String driver = "com.mysql.jdbc.Driver";

    public void getConnection() {
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(sql_url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("ServletContextListener started");
        getConnection();
        event.getServletContext().setAttribute("connection", connection);
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se) {
            // Do something
        }
    }
}


