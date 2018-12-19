import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.CoreMatchers.containsString;

public class BudgetTrackerTest {
    @Test
    public void createNewBudgetTracker_welcomesUser() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayInputStream userInput = new ByteArrayInputStream("1".getBytes());
        CommandLineInterface cli = new CommandLineInterface(new PrintStream(outContent), userInput);
        Message message = new Message();
        Display display = new Display(cli, message);

        BudgetTracker budgetTracker = new BudgetTracker(display);

        budgetTracker.start();
        String output = outContent.toString();

        Assert.assertThat(output, containsString("Welcome"));
    }
}

