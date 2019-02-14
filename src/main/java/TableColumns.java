public enum TableColumns {
    NAME("name"),
    DESCRIPTION("description"),
    COST("proposed_cost");

    private String columnName;

    TableColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
