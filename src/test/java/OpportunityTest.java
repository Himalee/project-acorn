import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OpportunityTest {

    private Opportunity opportunity;

    @Before
    public void setUp() {
        String name = "Clojure MeetUp";
        String description = "Covering the Accessibility and Inclusion Options at ClojureBridge Meetup";
        opportunity = new Opportunity(name, description);

    }

    @Test
    public void createNewOpportunity_name() {
        Assert.assertEquals("Clojure MeetUp", opportunity.name);
    }

    @Test
    public void getOpportunity_description() {
        String opportunityDescription = "Covering the Accessibility and Inclusion Options at ClojureBridge Meetup";
        Assert.assertEquals(opportunityDescription, opportunity.description);
    }
}
