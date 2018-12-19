import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CommandLineInterfaceTest {

    private CommandLineInterface cli;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        ByteArrayInputStream userInput = new ByteArrayInputStream("FooBar".getBytes());
        cli = new CommandLineInterface(new PrintStream(outContent), userInput);
    }

    @Test
    public void getUserInput_fooBar() {
        Assert.assertEquals("FooBar", cli.receiveString());
    }

    @Test
    public void display_helloWorld() {
        String message = "Hello, World";
        cli.present(message);

        Assert.assertEquals("Hello, World\n", outContent.toString());
    }
}
