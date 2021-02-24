package epam.utils;

import by.epam.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.Assert;
//import org.junit.Test;

class StringUtilsTest {
    @Test
    void isPositiveNumber_null_false() {
        Assertions.assertFalse(StringUtils.isPositiveNumber(null));
    }

    @Test
    void isPositiveNumber_emptyString_false() {
        Assertions.assertFalse(StringUtils.isPositiveNumber(""));
    }

    @Test
    void isPositiveNumber_positiveNumber_true() {
        Assertions.assertTrue(StringUtils.isPositiveNumber("123"));
    }

    @Test
    void isPositiveNumber_notNumber_false() {
        Assertions.assertFalse(StringUtils.isPositiveNumber("123q"));
    }

    @Test
    void isPositiveNumber_negativeNumber_false() {
        Assertions.assertFalse(StringUtils.isPositiveNumber("-123"));
    }
}