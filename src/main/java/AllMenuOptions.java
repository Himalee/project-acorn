public enum AllMenuOptions {

    ADD_NEW_OPP("a", "Add new opportunity", startingMenu()),
    DISPLAY_ALL_OPP("d", "Display all opportunities", startingMenu()),
    SEARCH_BY_ID("i", "Search by id", startingMenu()),
    UPDATE_OPP("u", "Update an opportunity", startingMenu()),
    QUIT("q", "Quit", startingMenu()),
    TO_BE_DISCUSSED("t", toBeDiscussed(), opportunityStagesMenu()),
    IN_DISCUSSION("i", inDiscussion(), opportunityStagesMenu()),
    APPROVED("a", approved(), opportunityStagesMenu()),
    DECLINED("d", declined(), opportunityStagesMenu()),
    EXPIRED("x", expired(), opportunityStagesMenu()),
    NAME("n", "Update name", updateOpportunityMenu()),
    DESCRIPTION("d", "Update description", updateOpportunityMenu()),
    COST("c", "Update cost", updateOpportunityMenu()),
    USER_NAME("r", "Update user name", updateOpportunityMenu());

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

    public static String toBeDiscussed() {
        return OpportunityStages.TO_BE_DISCUSSED.getStage();
    }

    public static String inDiscussion() {
        return OpportunityStages.IN_DISCUSSION.getStage();
    }

    public static String approved() {
        return OpportunityStages.APPROVED.getStage();
    }

    public static String declined() {
        return OpportunityStages.DECLINED.getStage();
    }

    public static String expired() {
        return OpportunityStages.EXPIRED.getStage();
    }
}
