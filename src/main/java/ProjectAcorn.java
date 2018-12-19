public class ProjectAcorn {

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface(System.out, System.in);
        Message message = new Message();
        Display display = new Display(cli, message);
        BudgetTracker budgetTracker = new BudgetTracker(display);
        budgetTracker.start();
    }
}
