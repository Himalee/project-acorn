public enum Menus {

    STARTING("starting menu"),
    OPPORTUNITY_STAGES("opportunity stages"),
    UPDATE_OPPORTUNITY("update opportunity");

    private String menu;

    Menus(String menu) {
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }
}
