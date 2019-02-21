import java.util.ArrayList;
import java.util.List;

public class OptionList {

    private final List<AllMenuOptions> list;

    public OptionList() {
        this.list = new ArrayList<>();
    }

    public List<AllMenuOptions> getList() {
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
