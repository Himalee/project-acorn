public class BudgetTracker {

    public Display display;

    public BudgetTracker(Display display) {
        this.display = display;
    }

    public void start() {
       display.welcomeUser();
       display.menu();
       display.getUserMenuChoice();
       display.goodbye();
    }
}
