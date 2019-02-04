public class Opportunity {

    private int id;
    private String name;
    private String description;
    private int proposedCost;
    private String userName;

    public Opportunity(String name, String description, int proposedCost, String userName) {
        this.name = name;
        this.description = description;
        this.proposedCost = proposedCost;
        this.userName = userName;
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
}
