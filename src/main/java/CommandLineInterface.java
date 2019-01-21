import java.io.*;
import java.util.Scanner;

public class CommandLineInterface {

    private PrintStream output;
    private final Scanner userInput;

    public CommandLineInterface(PrintStream output, InputStream input) {
        this.output = output;
        userInput = new Scanner(input);
    }

    public void present(String message) {
        output.println(message);
    }

    public String receiveString() {
        return userInput.nextLine();
    }

    public int receiveInteger() {
        String input = receiveString();
        return Integer.parseInt(input);
    }
}
