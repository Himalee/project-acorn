import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.CoreMatchers.containsString;

public class BudgetTrackerTest {
    @Test
    public void createNewBudgetTracker_welcomesUser() {
        ByteArrayInputStream userInput = new ByteArrayInputStream("1".getBytes());
        Display display = new Display(userInput);

        BudgetTracker budgetTracker = new BudgetTracker(display);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        budgetTracker.start();
        String output = outContent.toString();

        Assert.assertThat(output, containsString("Welcome"));
    }
}

