import java.io.*;
import java.util.Scanner;

public class CommandLineInterface {

    public PrintStream output;
    public InputStream input;
    private final Scanner userInput;

    public CommandLineInterface(PrintStream output, InputStream input) {
        this.output = output;
        this.input = input;
        userInput = new Scanner(input);
    }

    public void present(String message) {
        output.println(message);
    }

    public String receiveString() {
        String result = userInput.nextLine();
        return result;
    }
}
