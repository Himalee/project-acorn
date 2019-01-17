public enum MenuOptions {

    QUIT("q", "Quit"),
    ADD_NEW_OPP("a", "Add new opportunity"),
    DISPLAY_ALL_OPP("d", "Display all opportunities");

    private String command;
    private String name;

    MenuOptions(String command, String name) {
        this.command = command;
        this.name = name;
    }

    public static MenuOptions findChoice(String menuChoice) {
        for (MenuOptions menuOption : values()) {
            if (menuOption.command.equals(menuChoice)) {
                return menuOption;
            }
        }
        throw new IllegalArgumentException(menuChoice);
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
       return command;
    }
}