import java.io.InputStream;
import java.util.Scanner;

public class Display {

    public InputStream input;

    public Display(InputStream input) {
        this.input = input;
    }

    public void present(String message) {
        System.out.println(message);
    }

    public String receiveString() {
        Scanner userInput = new Scanner(input);
        String result = userInput.next();
        return result;
    }
}

