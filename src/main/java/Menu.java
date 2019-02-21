import java.util.List;

public class Menu {

    private final OptionList optionList;

    public Menu(OptionList optionList) {
        this.optionList = optionList;

    }

    public List<AllMenuOptions> getOptionList() {
        return optionList.getList();
    }

    public AllMenuOptions findMenuOption(String selectedCommand) {
        return optionList.getOption(selectedCommand);
    }
}
