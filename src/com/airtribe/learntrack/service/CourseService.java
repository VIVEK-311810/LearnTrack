package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.enums.CourseStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class CourseService {
    private CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks)
            throws InvalidInputException {
        InputValidator.validateNonEmptyString(courseName, "Course name");
        InputValidator.validateNonEmptyString(description, "Description");
        InputValidator.validatePositiveNumber(durationInWeeks, "Duration");

        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks, CourseStatus.ACTIVE);
        repository.save(course);
        return course;
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public List<Course> getActiveCourses() {
        return repository.findAllActive();
    }

    public void updateCourse(int id, String courseName, String description, int durationInWeeks)
            throws EntityNotFoundException, InvalidInputException {
        Course course = findCourseById(id);
        InputValidator.validateNonEmptyString(courseName, "Course name");
        InputValidator.validateNonEmptyString(description, "Description");
        InputValidator.validatePositiveNumber(durationInWeeks, "Duration");

        course.setCourseName(courseName);
        course.setDescription(description);
        course.setDurationInWeeks(durationInWeeks);
        repository.update(course);
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setStatus(CourseStatus.INACTIVE);
        repository.update(course);
    }

    public void activateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setStatus(CourseStatus.ACTIVE);
        repository.update(course);
    }

    public void archiveCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setStatus(CourseStatus.ARCHIVED);
        repository.update(course);
    }

    public void removeCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        repository.delete(course);
    }
}
