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
        assertTrue(validator.menuChoice("q", MenuTypes.STARTING_MENU.getType()));
        assertTrue(validator.menuChoice("a", MenuTypes.STARTING_MENU.getType()));
        assertTrue(validator.menuChoice("d", MenuTypes.STARTING_MENU.getType()));
    }

    @Test
    public void invalidMenuChoice_false() {
        assertFalse(validator.menuChoice("x", MenuTypes.STARTING_MENU.getType()));
        assertFalse(validator.menuChoice("y", MenuTypes.STARTING_MENU.getType()));
        assertFalse(validator.menuChoice("z", MenuTypes.STARTING_MENU.getType()));
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
        assertTrue(validator.onlyLetters("abc"));
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
        assertTrue(validator.menuChoice("t", MenuTypes.OPPORTUNITY_STAGES_MENU.getType()));
        assertTrue(validator.menuChoice("i", MenuTypes.OPPORTUNITY_STAGES_MENU.getType()));
        assertTrue(validator.menuChoice("a", MenuTypes.OPPORTUNITY_STAGES_MENU.getType()));
    }

    @Test
    public void invalidOpportunityStageChoice_false() {
        assertFalse(validator.menuChoice("e", MenuTypes.OPPORTUNITY_STAGES_MENU.getType()));
        assertFalse(validator.menuChoice("f", MenuTypes.OPPORTUNITY_STAGES_MENU.getType()));
        assertFalse(validator.menuChoice("g", MenuTypes.OPPORTUNITY_STAGES_MENU.getType()));
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
        assertTrue(validator.menuChoice("n", MenuTypes.UPDATE_OPPORTUNITY_MENU.getType()));
    }

    @Test
    public void invalidUpdateOpportunityChoice_false() {
        assertFalse(validator.menuChoice("a", MenuTypes.UPDATE_OPPORTUNITY_MENU.getType()));
    }
}
