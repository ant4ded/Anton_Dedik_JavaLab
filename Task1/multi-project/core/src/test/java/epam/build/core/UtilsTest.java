package epam.build.core;

import by.epam.build.core.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilsTest {
    @Test
    void isAllPositiveNumbers_oneNull_false() {
        Assertions.assertFalse(Utils.isAllPositiveNumbers(null, "4535", "67"));
    }

    @Test
    void isAllPositiveNumbers_empty_false() {
        Assertions.assertFalse(Utils.isAllPositiveNumbers("", "4535", "67"));
    }

    @Test
    void isAllPositiveNumbers_positiveNumbers_true() {
        Assertions.assertTrue(Utils.isAllPositiveNumbers("123", "4535", "67"));
    }

    @Test
    void isAllPositiveNumbers_notAllPositiveNumbers_false() {
        Assertions.assertFalse(Utils.isAllPositiveNumbers("-123", "4535", "67"));
    }

    @Test
    void isAllPositiveNumbers_notAllNumbers_false() {
        Assertions.assertFalse(Utils.isAllPositiveNumbers("123q", "4535", "67"));
    }
}