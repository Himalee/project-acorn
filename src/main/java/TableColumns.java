public enum TableColumns {
    NAME("name"),
    DESCRIPTION("description"),
    COST("proposed_cost"),
    USER_NAME("user_name"),
    STAGE("stage"),
    ID("id"),
    UUID("uuid"),
    OPPORTUNITY_UUID("opportunity_uuid");

    private String columnName;

    TableColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
