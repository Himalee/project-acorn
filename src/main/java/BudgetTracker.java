public class BudgetTracker {

    public Display display;

    public BudgetTracker(Display display) {
        this.display = display;
    }

    public void start() {
        welcomeUser();
        displayMenu();
        userMenuChoice();
        goodbye();
    }

    private void welcomeUser() {
        display.present("Welcome to Project Acorn");
    }

    private void displayMenu() {
        display.present("Menu:\n1. Quit\nPlease enter your choice:");
    }

    private void userMenuChoice() {
        display.receiveString();
    }

    private void goodbye() {
        display.present("Goodbye");
    }
}
