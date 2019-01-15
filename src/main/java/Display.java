public class Display {

    private CommandLineInterface cli;

    public Display(CommandLineInterface cli) {
        this.cli = cli;
    }

    public void welcomeUser() {
        cli.present(Message.welcome());
    }

    public void menu() {
        cli.present(Message.userMenu());
    }

    public String getUserInput() {
        return cli.receiveString();
    }

    public void getOpportunityName() {
        cli.present(Message.enterOpportunityName());
    }

    public void opportunityWrittenToDatabase() {
        cli.present(Message.opportunitySaved());
    }

    public void goodbye() {
        cli.present(Message.closeApp());
    }

    public void listAllOpportunities(String opportunities) {
        cli.present(opportunities);
    }
}

