package cz.vse.adventurefx.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void generatedPassword_IsEightDigitsLong() {
        String password = Password.password;
        assertEquals(8, password.length(), "Password should have 8 digits");
    }

    @Test
    void generatedPassword_ContainsOnlyDigits() {
        String password = Password.password;
        assertTrue(password.matches("\\d{8}"), "Password should contain only digits");
    }
}
