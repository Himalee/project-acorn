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
        assertTrue(validator.menuChoice("q"));
        assertTrue(validator.menuChoice("a"));
        assertTrue(validator.menuChoice("d"));
    }

    @Test
    public void invalidMenuChoice_false() {
        assertFalse(validator.menuChoice("x"));
        assertFalse(validator.menuChoice("y"));
        assertFalse(validator.menuChoice("z"));
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
}
