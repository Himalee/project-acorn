public enum MenuTypes {

    STARTING_MENU("starting menu"),
    OPPORTUNITY_STAGES_MENU("opportunity stages"),
    UPDATE_OPPORTUNITY_MENU("update opportunity");

    private String type;

    MenuTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
