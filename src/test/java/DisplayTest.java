import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

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
        HashMap<String, ArrayList> names = new HashMap<>();
        ArrayList<String> helloWorld = new ArrayList<>();
        helloWorld.add("Hello");
        helloWorld.add("World");
        ArrayList<String> fooBar = new ArrayList<>();
        fooBar.add("Foo");
        fooBar.add("Bar");
        names.put("1", helloWorld);
        names.put("2", fooBar);
        String expectedOutput = "1. Hello\nWorld\n2. Foo\nBar\n\n";

        display.formatOpportunities(names);

        Assert.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void menuChoices_convertToUserFriendlyDisplay() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = createNewCLI(outContent, "");
        Display display = new Display(cli, validator);
        String expectedOutput = "Quit (select q)\nAdd new opportunity (select a)\nDisplay all opportunities (select d)\n\n";

        display.formatMenu();

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
        CommandLineInterface cli = createNewCLI(outContent, "123\n123.45\n");
        Display display = new Display(cli, validator);

        display.getCost();
        Assert.assertThat(outContent.toString(), containsString("Please enter the proposed cost"));
    }
}
