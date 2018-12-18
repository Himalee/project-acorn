import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DisplayTest {
    @Test
    public void getUserInput_fooBar() {
        Display display = new Display();
        ByteArrayInputStream input = new ByteArrayInputStream("FooBar".getBytes());

        Assert.assertEquals("FooBar", display.receive(input));
    }

    @Test
    public void display_helloWorld() {
        Display display = new Display();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String message = "Hello, World";
        display.present(message);

        String expectedOutput = "Hello, World\n";

        Assert.assertEquals(expectedOutput, outContent.toString());
    }
}
