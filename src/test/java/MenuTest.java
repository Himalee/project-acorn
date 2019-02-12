import org.junit.Assert;
import org.junit.Test;

public class MenuTest {
    @Test
    public void newMenu_findMenuOptionDescription() {
        OptionList startingMenuOptions = new OptionsBuilder().build(Menus.STARTING.getMenu());
        Menu startingMenu = new Menu(startingMenuOptions);
        String userChoice = "a";
        Assert.assertEquals("Add new opportunity", startingMenu.findMenuOption(userChoice).getDescription());
    }
}
