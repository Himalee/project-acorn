import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DisplayTest {

    private Display display;

    @Before
    public void setUp() {
        display = new Display();
    }

    @Test
    public void getUserInput_fooBar() {
        ByteArrayInputStream input = new ByteArrayInputStream("FooBar".getBytes());

        Assert.assertEquals("FooBar", display.receive(input));
    }

    @Test
    public void display_helloWorld() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String message = "Hello, World";
        display.present(message);

        String expectedOutput = "Hello, World\n";

        Assert.assertEquals(expectedOutput, outContent.toString());
    }
}
