package com.nestify.pg.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nestify_pg",
                "root",
                "230825"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}