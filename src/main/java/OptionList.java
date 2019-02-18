import java.util.ArrayList;

public class OptionList {

    private final ArrayList<AllMenuOptions> list;

    public OptionList() {
        this.list = new ArrayList<>();
    }

    public ArrayList<AllMenuOptions> getList() {
        return list;
    }

    public void add(AllMenuOptions menuOption) {
        list.add(menuOption);
    }

    public AllMenuOptions getOption(String selectedCommand) {
        for (AllMenuOptions option : list) {
            if (selectedCommand.equals(option.getCommand())) {
                return option;
            }
        }
        return null;
    }
}
