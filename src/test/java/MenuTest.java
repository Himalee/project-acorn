import org.junit.Assert;
import org.junit.Test;

public class MenuTest {
    @Test
    public void newMenu_findMenuOptionDescription() {
        OptionList startingMenuOptions = new OptionsBuilder().build(MenuTypes.STARTING_MENU.getType());
        Menu startingMenu = new Menu(startingMenuOptions);
        String userChoice = "a";
        Assert.assertEquals("Add new opportunity", startingMenu.findMenuOption(userChoice).getDescription());
    }
}
