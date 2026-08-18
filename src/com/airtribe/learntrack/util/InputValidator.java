package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public class InputValidator {
    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new InvalidInputException("Invalid email format");
        }
    }

    public static void validateName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
    }

    public static void validatePositiveInteger(int value) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException("Value must be a positive integer");
        }
    }

    public static void validateNonEmptyString(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
    }
}
