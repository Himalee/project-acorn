import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class DisplayTest {

    private Validator validator;

    @Before
    public void setUp() {
        validator = new Validator();
    }

    public CommandLineInterface createNewCLI(ByteArrayOutputStream outContent, String input) {
        ByteArrayInputStream userInput = new ByteArrayInputStream(input.getBytes());
        return new CommandLineInterface(new PrintStream(outContent), userInput);
    }

    @Test
    public void opportunities_convertToUserFriendlyDisplay() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "");
        Display display = new Display(cli, validator);

        List<Opportunity> opportunities = new ArrayList<>();
        Opportunity oppOne = new Opportunity("hello", "world", 120, "himalee", "approved");
        oppOne.setId(1);
        Opportunity oppTwo = new Opportunity("foo", "bar", 140, "tailor", "approved");
        oppTwo.setId(2);
        Opportunity oppThree = new Opportunity("goodbye", "world", 150, "becca", "approved");
        oppThree.setId(11);
        opportunities.add(oppOne);
        opportunities.add(oppTwo);
        opportunities.add(oppThree);

        display.opportunities(opportunities);
        String expectedOutput = "1. hello\nworld\n120\nhimalee\napproved\n2. foo\nbar\n140\ntailor\napproved\n11. goodbye\nworld\n150\nbecca\napproved\n\n";

        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void startingMenuChoices_convertToUserFriendlyDisplay() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "");
        Display display = new Display(cli, validator);
        String expectedOutput = "Add new opportunity (select a)\n" +
                "Display all opportunities (select d)\n" +
                "Search by id (select i)\n" +
                "Update an opportunity (select u)\n" +
                "Quit (select q)\n\n";

        display.startingMenu();

        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void invalidMenuChoice_displayErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "z\nq\n");
        Display display = new Display(cli, validator);

        display.getMenuChoice();
        Assert.assertThat(outContent.toString(), containsString("Invalid"));
    }

    @Test
    public void emptyUserInput_displayErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "\nq\n");
        Display display = new Display(cli, validator);

        display.getNonEmptyInput();
        Assert.assertThat(outContent.toString(), containsString("Please enter input"));
    }

    @Test
    public void invalidCostUserInput_displayCostPrompt() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "hello\n123\n12.1\n123.34");
        Display display = new Display(cli, validator);

        display.getCost();
        Assert.assertThat(outContent.toString(), containsString("Please enter the proposed cost"));
    }

    @Test
    public void invalidUserNameInput_displayUserInputPrompt() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "123\nHello 123\nHimalee");
        Display display = new Display(cli, validator);

        display.getOnlyLettersInput();
        Assert.assertThat(outContent.toString(), containsString("Please enter your name"));
    }

    @Test
    public void opportunityStagesMenuChoices_convertToUserFriendlyDisplay() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "");
        Display display = new Display(cli, validator);
        String expectedOutput = "To be discussed (select t)\n" +
                "In discussion (select i)\n" +
                "Approved (select a)\n" +
                "Declined (select d)\n" +
                "Expired (select x)\n\n";

        display.opportunityStagesMenu();

        Assert.assertEquals(expectedOutput, outContent.toString());
    }
}
