package com.todoapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CreateTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from request
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String status = "pending"; // Default status

        if (title == null || description == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Missing required parameters: title or description");
            return;
        }

        // Connect to the database and insert the task
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO tasks (title, description, status) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setString(3, status);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().println("Task created successfully!");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().println("Failed to create task.");
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }
    private void fetchAllTasks(HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM tasks";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                StringBuilder tasks = new StringBuilder();
                while (rs.next()) {
                    tasks.append("ID: ").append(rs.getInt("id"))
                            .append(", Title: ").append(rs.getString("title"))
                            .append(", Description: ").append(rs.getString("description"))
                            .append(", Status: ").append(rs.getString("status"))
                            .append("\n");
                }

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(tasks.toString());
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }

    private void fetchTaskById(String taskId, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM tasks WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(taskId));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String task = "ID: " + rs.getInt("id") +
                                ", Title: " + rs.getString("title") +
                                ", Description: " + rs.getString("description") +
                                ", Status: " + rs.getString("status");
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().println(task);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().println("Task not found");
                    }
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid Task ID format");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Fetch all tasks
            fetchAllTasks(response);
        } else {
            // Fetch task by ID
            String taskId = pathInfo.substring(1);
            fetchTaskById(taskId, response);
        }
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Task ID is required in the URL");
            return;
        }
        String taskId = pathInfo.substring(1);
        String status = request.getParameter("status");

        if (taskId == null || status == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Task ID and status are required");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE tasks SET status = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setInt(2, Integer.parseInt(taskId));

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("Task status updated successfully!");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Task not found");
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Task ID is required");
            return;
        }
        String taskId=pathInfo.substring(1);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM tasks WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(taskId));

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("Task deleted successfully!");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Task not found");
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }
}

