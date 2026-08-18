package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputValidator {
    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty()) {
            return;
        }
        if (!email.contains("@")) {
            throw new InvalidInputException("Invalid email format");
        }
    }

    public static void validateName(String name, String fieldName) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
    }

    public static void validatePositiveInteger(int value) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException("Value must be a positive integer");
        }
    }

    public static void validatePositiveNumber(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be greater than 0");
        }
    }

    public static void validateNonEmptyString(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
    }

    public static void validateDate(String dateStr) throws InvalidInputException {
        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Use YYYY-MM-DD");
        }
    }

    public static void validateEnrollmentStatus(String status) throws InvalidInputException {
        if (status == null || status.trim().isEmpty()) {
            throw new InvalidInputException("Status cannot be empty");
        }
        String upper = status.toUpperCase();
        if (!upper.equals("ACTIVE") && !upper.equals("COMPLETED") && !upper.equals("CANCELLED")) {
            throw new InvalidInputException("Invalid status. Use ACTIVE, COMPLETED, or CANCELLED");
        }
    }
}
