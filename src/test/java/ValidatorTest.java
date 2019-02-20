import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {

    private Validator validator;

    @Before
    public void setUp(){
        validator = new Validator();
    }

    @Test
    public void validMenuChoice_true() {
        assertTrue(validator.menuChoice("q", Menus.STARTING.getMenu()));
        assertTrue(validator.menuChoice("a", Menus.STARTING.getMenu()));
        assertTrue(validator.menuChoice("d", Menus.STARTING.getMenu()));
    }

    @Test
    public void invalidMenuChoice_false() {
        assertFalse(validator.menuChoice("v", Menus.STARTING.getMenu()));
        assertFalse(validator.menuChoice("y", Menus.STARTING.getMenu()));
        assertFalse(validator.menuChoice("z", Menus.STARTING.getMenu()));
    }

    @Test
    public void nonEmptyInput_false() {
        assertFalse(validator.empty("HelloWorld"));
    }

    @Test
    public void emptyInput_true() {
        assertTrue(validator.empty(""));
    }

    @Test
    public void incorrectlyFormattedCostInput_false() {
        assertFalse(validator.cost("123"));
        assertFalse(validator.cost("123.0"));
        assertFalse(validator.cost("123.123"));
        assertFalse(validator.cost("1000000"));
        assertFalse(validator.cost("123:00"));

    }

    @Test
    public void correctlyFormattedCostInput_true() {
        assertTrue(validator.cost("123.45"));
    }

    @Test
    public void onlyLettersInput_true() {
        assertTrue(validator.onlyLetters("abcDEF"));
        assertTrue(validator.onlyLetters("abc abc"));
        assertTrue(validator.onlyLetters("abc def GHI"));
        assertTrue(validator.onlyLetters(""));
    }


    @Test
    public void onlyLettersInput_false() {
        assertFalse(validator.onlyLetters("abc123"));
        assertFalse(validator.onlyLetters("123"));
        assertFalse(validator.onlyLetters("A123Bb"));
        assertFalse(validator.onlyLetters("Aabc./"));
    }

    @Test
    public void validOpportunityStageChoice_true() {
        assertTrue(validator.menuChoice("t", Menus.OPPORTUNITY_STAGES.getMenu()));
        assertTrue(validator.menuChoice("i", Menus.OPPORTUNITY_STAGES.getMenu()));
        assertTrue(validator.menuChoice("a", Menus.OPPORTUNITY_STAGES.getMenu()));
    }

    @Test
    public void invalidOpportunityStageChoice_false() {
        assertFalse(validator.menuChoice("e", Menus.OPPORTUNITY_STAGES.getMenu()));
        assertFalse(validator.menuChoice("f", Menus.OPPORTUNITY_STAGES.getMenu()));
        assertFalse(validator.menuChoice("g", Menus.OPPORTUNITY_STAGES.getMenu()));
    }

    @Test
    public void onlyNumbersInput_true() {
        assertTrue(validator.onlyNumbers("1"));
        assertTrue(validator.onlyNumbers("123"));
        assertTrue(validator.onlyNumbers("100"));
    }

    @Test
    public void onlyNumbersInput_false() {
        assertFalse(validator.onlyNumbers("1.00"));
        assertFalse(validator.onlyNumbers("123abc"));
        assertFalse(validator.onlyNumbers("abc"));
        assertFalse(validator.onlyNumbers("//.."));
    }

    @Test
    public void validUpdateOpportunityChoice_true() {
        assertTrue(validator.menuChoice("n", Menus.UPDATE_OPPORTUNITY.getMenu()));
    }

    @Test
    public void invalidUpdateOpportunityChoice_false() {
        assertFalse(validator.menuChoice("a", Menus.UPDATE_OPPORTUNITY.getMenu()));
    }

    @Test
    public void validConfirmationChoice_true() {
        assertTrue(validator.menuChoice("y", Menus.CONFIRMATION.getMenu()));
        assertTrue(validator.menuChoice("n", Menus.CONFIRMATION.getMenu()));

    }

    @Test
    public void invalidConfirmationChoice_false() {
        assertFalse(validator.menuChoice("yy", Menus.CONFIRMATION.getMenu()));
        assertFalse(validator.menuChoice("123", Menus.CONFIRMATION.getMenu()));
    }

    @Test
    public void invalidDate_false() {
        assertFalse(validator.date("01-13-20193"));
        assertFalse(validator.date("01/13/2019"));
        assertFalse(validator.date("01-13-2019"));
        assertFalse(validator.date("36-12-2019"));
        assertFalse(validator.date("01--13-2019"));
        assertFalse(validator.date("01132019"));
    }

    @Test
    public void validDate_true() {
        assertTrue(validator.date("01-12-2019"));
    }
}
