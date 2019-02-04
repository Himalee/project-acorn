import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OpportunityTest {

    private Opportunity opportunity;

    @Before
    public void setUp() {
        String name = "Clojure MeetUp";
        String description = "Covering the Accessibility and Inclusion Options at ClojureBridge Meetup";
        int proposedCost = 30000;
        String userName = "Himalee";
        opportunity = new Opportunity(name, description, proposedCost, userName);

    }

    @Test
    public void getOpportunity_name() {
        Assert.assertEquals("Clojure MeetUp", opportunity.name);
    }

    @Test
    public void getOpportunity_description() {
        String opportunityDescription = "Covering the Accessibility and Inclusion Options at ClojureBridge Meetup";
        Assert.assertEquals(opportunityDescription, opportunity.description);
    }

    @Test
    public void getOpportunity_proposedCost() {
        Assert.assertEquals(30000, opportunity.proposedCost);
    }

    @Test
    public void getOpportunity_userName() {
        Assert.assertEquals("Himalee", opportunity.userName);
    }
}
