package org.example.db;
import com.opencsv.exceptions.CsvValidationException;
import org.example.enums.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import org.postgresql.util.PGobject;
import io.github.cdimascio.dotenv.Dotenv;

// use the TableCols interface to handle different enums dynamically
public class DataLoader {
    static Dotenv dotenv = Dotenv.load();
    static Map<String, String> pKeys = new HashMap<>();

    public static void primaryKeys() {
        pKeys.put("categories", "id");
        pKeys.put("ingredients", "id");
        pKeys.put("recipes", "id");
        pKeys.put("tags", "id");
        pKeys.put("types", "id");
        pKeys.put("recipes_ingredients", "recipe_id, ingredient_id");
        pKeys.put("recipes_tags", "recipe_id, tag_id");
        pKeys.put("recipes_instructions", "id");
        pKeys.put("user_progress", "user_id, step_id");
    }

    // check if a table already being created
    private static boolean checkTableExistence(String tableName) {
        try (Connection conn = TestConnect.dataSource().getConnection()) {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet rs = dbm.getTables(null, null, tableName, null);
            if (rs.next()) {
                System.out.println("TABLE " + tableName + " EXISTS.");
                return true;
            } else {
                System.out.println("TABLE " + tableName + " DOES NOT EXIST!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while checking the existence of TABLE " + tableName);
            return false;
        }
    }

    public static void executeSqlSchema(String resourcePath) {
        String allTableString = dotenv.get("ALL_TABLES");
        String[] tableNames = allTableString.split(",");
        boolean allExistence = true;
        for (String tableName : tableNames) {
            if (!checkTableExistence(tableName)) {
                allExistence = false;
            }
        }
        if (!allExistence) {
            try (InputStream inputStream = DataLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
                System.out.println(inputStream);
                if (inputStream == null) {
                    throw new RuntimeException("Schema file not found in classpath: " + resourcePath);
                }
                try (Connection conn = TestConnect.dataSource().getConnection();
                     Statement stmt = conn.createStatement()) {
                    String sqlString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    stmt.execute(sqlString);
                    System.out.println("Schema created successfully!");
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to execute SQL schema", e);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read schema file: " + resourcePath, e);
            }
        } else {
            System.out.println("All tables already exist, skip the execution.");
        }
    }

    // dynamically build the SQL INSERT statement using column names and indices provided by TableCols interface
    public static void loadCsvToTable(String resourcePath, String tableName, BaseColumnEnum[] columns) {
        try (Connection conn = TestConnect.dataSource().getConnection();
             InputStream inputStream = DataLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            System.out.println("input stream: " + inputStream);
            if (inputStream == null) {
                throw new RuntimeException("CSV file not found in classpath: " + resourcePath);
            }
            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder insertBuilder = new StringBuilder("INSERT INTO ").append(tableName).append("(");
                for (BaseColumnEnum column: columns) {
                    insertBuilder.append(column.getColumnName()).append(", ");
                }
                insertBuilder.setLength(insertBuilder.length() - 2);
                insertBuilder.append(") VALUES (").append("?, ".repeat(columns.length));
                insertBuilder.setLength(insertBuilder.length() - 2);
                insertBuilder.append(") ON CONFLICT (").append(pKeys.get(tableName)).append(") DO NOTHING");
                String sqlInsert = insertBuilder.toString();

                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                    String[] nextLine = reader.readNext();
                    while ((nextLine = reader.readNext()) != null) {
                        for (BaseColumnEnum column: columns) {
                            int colIndex = column.getColumnIndex();
                            int colType = column.getColumnType();
                            int paramIndex = colIndex + 1;
                            if (nextLine[colIndex] != null && !nextLine[colIndex].isEmpty()) {
                                try {
                                    switch (colType) {
                                        case Types.VARCHAR:
                                        case Types.LONGNVARCHAR:
                                            pstmt.setString(paramIndex, nextLine[colIndex]);
                                            break;
                                        case Types.INTEGER:
                                            pstmt.setInt(paramIndex, Integer.parseInt(nextLine[colIndex]));
                                            break;
                                        case Types.NUMERIC:
                                            pstmt.setDouble(paramIndex, Double.parseDouble(nextLine[colIndex]));
                                            break;
                                        case Types.TIMESTAMP:
                                            pstmt.setTimestamp(paramIndex, Timestamp.valueOf(nextLine[colIndex]));
                                            break;
                                        case Types.OTHER:
                                            PGobject jsonObject = new PGobject();
                                            jsonObject.setType("jsonb");
                                            jsonObject.setValue(nextLine[colIndex]);
                                            pstmt.setObject(paramIndex, jsonObject);
                                            break;
                                        default:
                                            pstmt.setObject(paramIndex, nextLine[colIndex]);
                                    }
                                } catch (NumberFormatException e) {
                                    throw new RuntimeException("Failed to parse value '" + nextLine[colIndex] + "' for column " + column.getColumnName(), e);
                                }
                            } else {
                                pstmt.setNull(paramIndex, colType);
                            }
                        }
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }
        } catch (SQLException | IOException | CsvValidationException e) {
                String context = e instanceof SQLException ? "Database error while loading CSV data for table " + tableName :
                                 e instanceof IOException ? "IO error while reading CSV file " + resourcePath :
                                                            "CSV validation error in file " + resourcePath;
                throw new RuntimeException(context + ": " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        executeSqlSchema("sqlFiles/schema.sql");
        primaryKeys();
        loadCsvToTable("data/recipes.csv", "recipes", RecipeCols.values());
        loadCsvToTable("data/categories.csv", "categories", CategoryCols.values());
        loadCsvToTable("data/types.csv", "types", TypeCols.values());
        loadCsvToTable("data/tags.csv", "tags", TagCols.values());
        loadCsvToTable("data/ingredients.csv", "ingredients", IngredientCols.values());
        loadCsvToTable("data/recipes_ingredients.csv", "recipes_ingredients", RecipeIngredientCols.values());
        loadCsvToTable("data/recipes_tags.csv", "recipes_tags", RecipeTagCols.values());
        loadCsvToTable("data/recipes_instructions.csv", "recipes_instructions", RecipeInstructionCols.values());
    }
}
