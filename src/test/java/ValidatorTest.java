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
        assertTrue(validator.startingMenuChoice("q"));
        assertTrue(validator.startingMenuChoice("a"));
        assertTrue(validator.startingMenuChoice("d"));
    }

    @Test
    public void invalidMenuChoice_false() {
        assertFalse(validator.startingMenuChoice("x"));
        assertFalse(validator.startingMenuChoice("y"));
        assertFalse(validator.startingMenuChoice("z"));
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
        assertTrue(validator.opportunityStageChoice("t"));
        assertTrue(validator.opportunityStageChoice("i"));
        assertTrue(validator.opportunityStageChoice("a"));
    }

    @Test
    public void invalidOpportunityStageChoice_false() {
        assertFalse(validator.opportunityStageChoice("e"));
        assertFalse(validator.opportunityStageChoice("f"));
        assertFalse(validator.opportunityStageChoice("g"));
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
}
