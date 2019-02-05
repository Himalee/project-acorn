public enum OpportunityStages implements Menu {

    TO_BE_DISCUSSED("t", "To be discussed"),
    IN_DISCUSSION("i", "In discussion"),
    APPROVED("a", "Approved"),
    DECLINED("d", "Declined"),
    EXPIRED("x", "Expired");

    private String command;
    private String name;

    OpportunityStages(String command, String name) {
        this.command = command;
        this.name = name;
    }

    public static OpportunityStages findStage(String stageChoice) {
        for (OpportunityStages stage : values()) {
            if (stage.command.equals(stageChoice)) {
                return stage;
            }
        }
        throw new IllegalArgumentException(stageChoice);
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }
}
