package com.airtribe.learntrack.enums;

public enum EnrollmentStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED;

    public static EnrollmentStatus fromString(String status) {
        if (status == null) {
            return null;
        }
        for (EnrollmentStatus enrollmentStatus : EnrollmentStatus.values()) {
            if (enrollmentStatus.name().equalsIgnoreCase(status)) {
                return enrollmentStatus;
            }
        }
        return null;
    }
}
