public enum AllMenuOptions {

    ADD_NEW_OPP("a", "Add new opportunity", startingMenu()),
    DISPLAY_ALL_OPP("d", "Display all opportunities", startingMenu()),
    SEARCH_BY_ID("i", "Search by id", startingMenu()),
    UPDATE_OPP("u", "Update an opportunity", startingMenu()),
    QUIT("q", "Quit", startingMenu()),
    TO_BE_DISCUSSED("t", "To be discussed", opportunityStagesMenu()),
    IN_DISCUSSION("i", "In discussion", opportunityStagesMenu()),
    APPROVED("a", "Approved", opportunityStagesMenu()),
    DECLINED("d", "Declined", opportunityStagesMenu()),
    EXPIRED("x", "Expired", opportunityStagesMenu()),
    NAME("n", "Update name", updateOpportunityMenu());

    private String command;
    private String description;
    private String type;

    AllMenuOptions(String command, String description, String type) {
        this.command = command;
        this.description = description;
        this.type = type;

    }

    public String getDescription() {
        return description;
    }

    public String getCommand() {
        return command;
    }

    public String getType() {
        return type;
    }

    public static String startingMenu() {
        return Menus.STARTING.getMenu();
    }

    public static String opportunityStagesMenu() {
        return Menus.OPPORTUNITY_STAGES.getMenu();
    }

    public static String updateOpportunityMenu() {
        return Menus.UPDATE_OPPORTUNITY.getMenu();
    }
}
