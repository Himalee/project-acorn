public class Opportunity implements Comparable<Opportunity> {

    private int id;
    private String uuid;
    private String name;
    private String description;
    private int proposedCost;
    private String userName;
    private String stage;

    public Opportunity(String name, String description, int proposedCost, String userName, String stage, String uuid) {
        this.name = name;
        this.description = description;
        this.proposedCost = proposedCost;
        this.userName = userName;
        this.stage = stage;
        this.uuid = uuid;
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

    @Override
    public int compareTo(Opportunity o) {
        return this.getId() - o.getId();
    }


}
