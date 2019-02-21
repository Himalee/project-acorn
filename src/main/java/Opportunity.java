public class Opportunity implements Comparable<Opportunity> {

    private int id;
    private String uuid;
    private String name;
    private String description;
    private int proposedCost;
    private String userName;
    private String stage;
    private String date;

    public Opportunity(String name, String description, int proposedCost, String userName, String stage, String uuid, String date) {
        this.name = name;
        this.description = description;
        this.proposedCost = proposedCost;
        this.userName = userName;
        this.stage = stage;
        this.uuid = uuid;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getProposedCost() {
        return proposedCost;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(Opportunity opportunity) {
        return this.getId() - opportunity.getId();
    }


}
