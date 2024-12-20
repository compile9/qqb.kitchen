package org.example.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            for (String sqlPath: sqlPaths) {
                String sqlString = new String(Files.readAllBytes(Paths.get(sqlPath)), StandardCharsets.UTF_8);
                stmt.execute(sqlString);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to create SQL function", e);
        }
    }

    public static void main(String[] args) {
        List<String> sqlPaths = List.of(
                "src/main/resources/sqlFiles/recipeFunctions.sql"
        );
        SqlFileExecutor exec = new SqlFileExecutor(sqlPaths);
        exec.executeSqlFile();
    }
}