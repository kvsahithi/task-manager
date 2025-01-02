package com.todoapp.servlets;

import jakarta.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection extends HttpServlet {

        private static final String URL = "jdbc:postgresql://localhost:5432/tasklist";
        private static final String USER = "sahithi";
        private static final String PASSWORD = "password";
        public static Connection getConnection() throws SQLException {
            try {
               Class.forName("org.postgresql.Driver");
               return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException | ClassNotFoundException e) {
                throw new SQLException("Database connection error", e);
        }
    }
    }

