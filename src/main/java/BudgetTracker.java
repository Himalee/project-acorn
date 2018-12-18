public class BudgetTracker {

    public Display display;

    public BudgetTracker(Display display) {
        this.display = display;
    }

    public void start() {
        welcomeUser();
        displayMenu();
        getUserMenuChoice();
        goodbye();
    }

    private void welcomeUser() {
        display.present("Welcome to Project Acorn");
    }

    private void displayMenu() {
        display.present("Menu:\n1. Quit\nPlease enter your choice:");
    }

    private void getUserMenuChoice() {
        display.receiveString();
    }

    private void goodbye() {
        display.present("Goodbye");
    }
}
