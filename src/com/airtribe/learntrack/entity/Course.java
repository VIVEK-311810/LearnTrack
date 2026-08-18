package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.enums.CourseStatus;

public class Course {
    private final int id;
    private String courseName;
    private String description;
    private int durationInWeeks;
    private CourseStatus status;
    private int trainerId;

    public Course(int id, String courseName, String description, int durationInWeeks, CourseStatus status) {
        if (durationInWeeks <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.status = status;
        this.trainerId = -1;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(int durationInWeeks) {
        if (durationInWeeks <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        this.durationInWeeks = durationInWeeks;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public boolean isActive() {
        return status == CourseStatus.ACTIVE;
    }

    public void setActive(boolean active) {
        this.status = active ? CourseStatus.ACTIVE : CourseStatus.INACTIVE;
    }
}
