import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DisplayTest {
    @Test
    public void display_HelloWorld() {
        Display display = new Display();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String message = "Hello, World";
        display.present(message);

        String expectedOutput = "Hello, World\n";

        Assert.assertEquals(expectedOutput, outContent.toString());

    }
}
