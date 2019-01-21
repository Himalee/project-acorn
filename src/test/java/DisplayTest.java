import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DisplayTest {
    @Test
    public void opportunities_convertToUserFriendlyDisplay() {
        ByteArrayInputStream userInput = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = new CommandLineInterface(new PrintStream(outContent), userInput);
        Display display = new Display(cli);
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
        ByteArrayInputStream userInput = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        CommandLineInterface cli = new CommandLineInterface(new PrintStream(outContent), userInput);
        Display display = new Display(cli);

        String expectedOutput = "Quit (select q)\nAdd new opportunity (select a)\nDisplay all opportunities (select d)\n\n";
        display.formatMenu();

        Assert.assertEquals(expectedOutput, outContent.toString());
    }
}
