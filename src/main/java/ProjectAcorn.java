public class ProjectAcorn {

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface(System.out, System.in);
        Display display = new Display(cli);
        BudgetTracker budgetTracker = new BudgetTracker(display);
        budgetTracker.start();
    }
}
