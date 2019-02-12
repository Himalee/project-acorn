import java.util.ArrayList;

public class Menu {

    private final OptionList optionList;

    public Menu(OptionList optionList) {
        this.optionList = optionList;

    }

    public ArrayList<AllMenuOptions> getOptionList() {
        return optionList.getList();
    }

    public AllMenuOptions findMenuOption(String selectedCommand) {
        return optionList.getOption(selectedCommand);
    }
}
