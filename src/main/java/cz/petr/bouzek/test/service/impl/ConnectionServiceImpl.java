package cz.petr.bouzek.test.service.impl;

import com.mysql.jdbc.MySQLConnection;
import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import cz.petr.bouzek.test.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


/***
 * Service to manage multiple MYSQL connections.
 */
@Component
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private static String DB_URL_PREFIX = "jdbc:mysql://";
    private static String VALID_QUERY = "SELECT version();";
    private static String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    private Map<DBConnectionDetail, Connection> connectionPool = new HashMap<>();

    /***
     * Retrieves connection from pool for specified connection detail or creates a new one if it doesn't exist.
     */
    public Connection getConnection(DBConnectionDetail connectionDetail) {
        log.info("Getting connection " + connectionDetail.getName());

        if (connectionPool.containsKey(connectionDetail)) {
            log.info("Connection found in pool.");
            return connectionPool.get(connectionDetail);
        }

        log.info("Starting new connection to " + connectionDetail.getHostname());
        Connection con = null;
        try {
            Class.forName(DRIVER_CLASS).newInstance();

            con = DriverManager.getConnection(DB_URL_PREFIX + connectionDetail.getHostname() + ":"
                    + connectionDetail.getPort() + "/" + connectionDetail.getDatabaseName(), connectionDetail.getUsername(), connectionDetail.getPassword());

            if (testConnection(con)) {
                log.info("Valid connection tested...");
                connectionPool.put(connectionDetail, con);
            } else {
                log.error("Invalid connection...");
            }
        } catch (ClassNotFoundException cla) {
            log.error("Class Not found exception..." + cla.getMessage());
        } catch (SQLException sqe) {
            log.error("SQL Exception..." + sqe.getMessage());
        } catch (Exception exe) {
            log.error("Exception occured while making DB Connection - " + exe.getMessage());
        }

        return con;
    }

    /***
     * Closes the specified connection and removes it from the connection pool.
     */
    public void closeConnection(DBConnectionDetail connectionDetail) {

        if (connectionDetail == null || !connectionPool.containsKey(connectionDetail))
            return;

        log.info("Closing connection " + connectionDetail.getName());

        Connection con = connectionPool.get(connectionDetail);
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            log.error("Exception occured while closing DBConnection - " + e.getMessage());
        }
        connectionPool.remove(connectionDetail);
    }


    private boolean testConnection(Connection con) {

        boolean flag = false;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = con.createStatement();

            rs = stmt.executeQuery(VALID_QUERY);
            while (rs.next()) {
                String v = rs.getString(1);
                if (v != null) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            log.error("SQL Exception error - " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("Error in closing Result Set - " + e.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error("Error in closing Statement " + e.getMessage());
                }
            }
        }
        return flag;
    }
}
