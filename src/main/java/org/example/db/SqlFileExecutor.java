package org.example.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlFileExecutor {
    private final List<String> sqlPaths;

    public SqlFileExecutor(List<String> sqlPaths) {
        this.sqlPaths = sqlPaths;
    }

    public void executeSqlFile() {
        try (Connection conn = TestConnect.dataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            for (String resourcePath : sqlPaths) {
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
                    if (inputStream == null) {
                        throw new RuntimeException("SQL file not found in classpath: " + resourcePath);
                    }
                    String sqlString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    stmt.execute(sqlString);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read SQL file: " + resourcePath, e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL function: " + e.getMessage(), e);
        }
    }


    public static void main(String[] args) {
        List<String> sqlPaths = List.of(
                "sqlFiles/recipeFunctions.sql"
        );
        SqlFileExecutor exec = new SqlFileExecutor(sqlPaths);
        exec.executeSqlFile();
    }
}