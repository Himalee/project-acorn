public class Display {

    public CommandLineInterface cli;

    public Display(CommandLineInterface cli) {
        this.cli = cli;
    }

    public void welcomeUser() {
        cli.present("Welcome to Project Acorn");
    }

    public void menu() {
        cli.present("Menu:\n1. Quit\nPlease enter your choice:");
    }

    public void getUserMenuChoice() {
        cli.receiveString();
    }

    public void goodbye() {
        cli.present("Goodbye");
    }

}

