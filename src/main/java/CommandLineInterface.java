import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandLineInterface {

    public PrintStream output;
    public InputStream input;

    public CommandLineInterface(PrintStream output, InputStream input) {
        this.output = output;
        this.input = input;
    }

    public void present(String message) {
        output.println(message);
    }

    public String receiveString() {
        Scanner userInput = new Scanner(input);
        String result = userInput.next();
        return result;
    }
}