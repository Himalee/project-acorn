public class Display {

    public CommandLineInterface cli;
    public Message message;

    public Display(CommandLineInterface cli, Message message) {
        this.cli = cli;
        this.message = message;
    }

    public void welcomeUser() {
        cli.present(message.welcome());
    }

    public void menu() {
        cli.present(message.userMenu());
    }

    public void getUserMenuChoice() {
        cli.receiveString();
    }

    public void goodbye() {
        cli.present(message.closeApp());
    }
}

