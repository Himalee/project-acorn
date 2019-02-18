public class OptionsBuilder {

    public OptionList build(String menuType) {
        OptionList filteredMenuOption = new OptionList();
        AllMenuOptions[] allOptions = AllMenuOptions.values();
            for (AllMenuOptions option : allOptions) {
                if (menuType.equals(option.getType())) {
                    filteredMenuOption.add(option);
                }
            }
        return filteredMenuOption;
    }
}
