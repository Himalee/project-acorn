public enum OpportunityStages {

    TO_BE_DISCUSSED("To be discussed"),
    IN_DISCUSSION("In discussion"),
    APPROVED("Approved"),
    DECLINED("Declined"),
    EXPIRED("Expired");

    private String stage;

    OpportunityStages(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }
}
