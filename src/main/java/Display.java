import java.io.InputStream;
import java.util.Scanner;

public class Display {

    public void present(String message) {
        System.out.println(message);
    }

    public String receive(InputStream inputStream) {
        Scanner input = new Scanner(inputStream);
        String result = input.next();
        return result;
    }
}

