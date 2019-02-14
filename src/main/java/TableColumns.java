public enum TableColumns {
    NAME("name"),
    DESCRIPTION("description"),
    COST("proposed_cost"),
    USER_NAME("user_name"),
    STAGE("stage");

    private String columnName;

    TableColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
