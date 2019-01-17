import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void formatOpportunities(HashMap<String, ArrayList> opportunitiesData) {
        StringBuilder opportunities = new StringBuilder();
        for (Map.Entry<String, ArrayList> entry : opportunitiesData.entrySet()) {
            String key = entry.getKey();
            opportunities.append(key);
            opportunities.append(". ");
            ArrayList values = entry.getValue();
            values.stream().forEach(elem -> opportunities.append(elem + "\n"));
        }
        cli.present(opportunities.toString());
    }
}

