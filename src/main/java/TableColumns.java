public enum TableColumns {
    NAME("name"),
    DESCRIPTION("description"),
    COST("proposed_cost"),
    USER_NAME("user_name"),
    STAGE("stage"),
    ID("id"),
    OPPORTUNITY_UUID("opportunity_uuid"),
    DATE("opportunity_date");

    private String columnName;

    TableColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
