import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DisplayTest {

    private Display display;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        ByteArrayInputStream userInput = new ByteArrayInputStream("FooBar".getBytes());
        display = new Display(new PrintStream(outContent), userInput);
    }

    @Test
    public void getUserInput_fooBar() {
        Assert.assertEquals("FooBar", display.receiveString());
    }

    @Test
    public void display_helloWorld() {
        String message = "Hello, World";
        display.present(message);

        Assert.assertEquals("Hello, World\n", outContent.toString());
    }
}
