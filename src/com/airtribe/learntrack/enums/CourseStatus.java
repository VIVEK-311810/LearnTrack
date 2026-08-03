package com.airtribe.learntrack.enums;

public enum CourseStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    ARCHIVED("ARCHIVED");

    private final String status;

    CourseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static CourseStatus fromString(String status) {
        for (CourseStatus courseStatus : CourseStatus.values()) {
            if (courseStatus.status.equalsIgnoreCase(status)) {
                return courseStatus;
            }
        }
        return null;
    }
}
