package br.com.dio.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class with utility functions to manage the connection to the MySQL Database.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionUtil {

    /**
     * Returns a connection to the MySQL database 'jdbc-java'.
     *
     * @return A connection to the MySQL database.
     * @throws SQLException If the connection cannot be established.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/jdbc-java",
                "jdbc-java",
                "jdbc-java"
        );
    }
}
