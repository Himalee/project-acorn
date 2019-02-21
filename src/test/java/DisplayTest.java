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
        Opportunity oppOne = new Opportunity("hello", "world", 120, "himalee", "approved", "abc", "01-01-2019");
        oppOne.setId(1);
        Opportunity oppTwo = new Opportunity("foo", "bar", 140, "tailor", "approved", "def", "01-01-2019");
        oppTwo.setId(2);
        Opportunity oppThree = new Opportunity("goodbye", "world", 150, "becca", "approved", "ghi", "01-01-2019");
        oppThree.setId(11);
        opportunities.add(oppOne);
        opportunities.add(oppTwo);
        opportunities.add(oppThree);

        display.opportunities(opportunities);
        String expectedOutput = "1. hello\nworld\n£1.20\nhimalee\napproved\n01-01-2019\n2. foo\nbar\n£1.40\ntailor\napproved\n01-01-2019\n11. goodbye\nworld\n£1.50\nbecca\napproved\n01-01-2019\n\n";

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
                "Delete an opportunity (select x)\n" +
                "Quit (select q)\n\n";

        OptionList startingMenuOptions = new OptionsBuilder().build(Menus.STARTING.getMenu());
        Menu opportunityStages = new Menu(startingMenuOptions);

        display.menu(opportunityStages);

        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void invalidMenuChoice_displayErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "z\nq\n");
        Display display = new Display(cli, validator);

        display.getMenuChoice(Menus.STARTING.getMenu());
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

        OptionList updateOpportunityMenuOptions = new OptionsBuilder().build(Menus.OPPORTUNITY_STAGES.getMenu());
        Menu opportunityStages = new Menu(updateOpportunityMenuOptions);

        display.menu(opportunityStages);

        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void invalidOpportunityDateUserInput_displayDatePrompt() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "123-12-2018\n01/01/2019\n05-06-2019");
        Display display = new Display(cli, validator);

        display.getDate();
        Assert.assertThat(outContent.toString(), containsString("Please enter the date"));
    }
}
