package com.weibo.datasys.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by tuoyu on 22/12/2016.
 */
public class MySqlConnector implements ServletContextListener {
    protected final Log logger = LogFactory.getLog(getClass());
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
            logger.info("Connect to " + sql_url + " OK");
        } catch (Exception e) {
            logger.error("Init Connection to DB Error:".concat(e.getMessage()));
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("ServletContextListener Start");
        getConnection();
        event.getServletContext().setAttribute("connection", connection);
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        logger.info("ServletContextListener destroyed");
        try {
            if (connection != null) {
                connection.close();
                logger.info("DB Connect close");
            }
            Driver mySqlDriver = DriverManager.getDriver(sql_url);
            DriverManager.deregisterDriver(mySqlDriver);
        } catch (Exception se) {
            logger.error("Could not deregister driver:".concat(se.getMessage()));
        }
    }
}


