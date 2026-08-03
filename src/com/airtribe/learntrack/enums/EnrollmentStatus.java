package com.airtribe.learntrack.enums;

public enum EnrollmentStatus {
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String status;

    EnrollmentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static EnrollmentStatus fromString(String status) {
        for (EnrollmentStatus enrollmentStatus : EnrollmentStatus.values()) {
            if (enrollmentStatus.status.equalsIgnoreCase(status)) {
                return enrollmentStatus;
            }
        }
        return null;
    }
}
