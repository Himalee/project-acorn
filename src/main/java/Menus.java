public enum Menus {

    STARTING("starting menu"),
    OPPORTUNITY_STAGES("opportunity stages"),
    UPDATE_OPPORTUNITY("update opportunity"),
    CONFIRMATION("confirm");

    private String menu;

    Menus(String menu) {
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }
}
