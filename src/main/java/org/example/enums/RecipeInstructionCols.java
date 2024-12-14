package org.example.enums;

import java.sql.Types;

public enum RecipeInstructionCols implements BaseColumnEnum {
    ID("id", 0, Types.INTEGER),
    RECIPE_ID("recipe_id", 1, Types.INTEGER),
    STEP_NUMBER("step_number", 2, Types.INTEGER),
    INSTRUCTION("instruction", 3, Types.LONGNVARCHAR);

    private final ColumnInfo columnInfo;
    RecipeInstructionCols(String columnName, int columnIndex, int columnType) {
        this.columnInfo = new ColumnInfo(columnName, columnIndex, columnType);
    }
    @Override public ColumnInfo getColumnInfo() {
        return columnInfo;
    }
}
