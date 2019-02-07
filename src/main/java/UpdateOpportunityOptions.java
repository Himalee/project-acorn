public enum UpdateOpportunityOptions implements Menu {

    NAME("n", "Update name");

    private String command;
    private String name;

    UpdateOpportunityOptions(String command, String name) {
        this.command = command;
        this.name = name;
    }

    public static UpdateOpportunityOptions findChoice(String menuChoice) {
        for (UpdateOpportunityOptions updateOpportunityOption : values()) {
            if (updateOpportunityOption.command.equals(menuChoice)) {
                return updateOpportunityOption;
            }
        }
        throw new IllegalArgumentException(menuChoice);
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }
}
