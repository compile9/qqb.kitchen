package org.example.enums;

import java.sql.Types;

public enum RecipeCols implements BaseColumnEnum {
    // each enum constant represents a column in the recipes table
    ID("id", 0, Types.INTEGER), // ID represents the "id" column with an index of 0
    TITLE("title", 1, Types.VARCHAR),
    DESCRIPTION("description", 2, Types.VARCHAR),
    RATING("rating", 3, Types.NUMERIC),
    IMAGE("image", 4, Types.LONGNVARCHAR),
    DURATION("duration", 5, Types.INTEGER),
    CREATED_AT("created_at", 6, Types.TIMESTAMP);

    private final ColumnInfo columnInfo;

    // constructor initializes a ColumnInfo object for each constant.
    RecipeCols(String columnName, int columnIndex, int columnType) {
        this.columnInfo = new ColumnInfo(columnName, columnIndex, columnType);
    }

    @Override
    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

}