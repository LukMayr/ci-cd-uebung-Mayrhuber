package com.example.cicd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    @Test
    void safeParseInt_validNumber_returnsValue() {
        assertEquals(42, TextUtils.safeParseInt("42"));
    }

    @Test
    void safeParseInt_invalidNumber_returnsZero() {
        assertEquals(0, TextUtils.safeParseInt("x"));
    }

    @Test
    void isPalindrome_caseInsensitive_expectedTrue() {
        assertTrue(TextUtils.isPalindrome("Anna"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "abba", "RaceCar"})
    void isPalindrome_palindromes_returnTrue(String input) {
        assertTrue(TextUtils.isPalindrome(input));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"hello", "ab", "Anna!"})
    void isPalindrome_otherInputs_returnFalse(String input) {
        assertFalse(TextUtils.isPalindrome(input));
    }
}
