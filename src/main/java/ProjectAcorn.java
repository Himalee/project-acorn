public class ProjectAcorn {

    public static void main(String[] args) {
        Display display = new Display(System.in);
        BudgetTracker budgetTracker = new BudgetTracker(display);
        budgetTracker.start();
    }
}
