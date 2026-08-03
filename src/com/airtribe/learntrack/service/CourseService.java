package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks)
            throws InvalidInputException {
        InputValidator.validateNonEmptyString(courseName, "Course name");
        InputValidator.validateNonEmptyString(description, "Description");
        InputValidator.validatePositiveInteger(durationInWeeks);

        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks, true);
        courseRepository.save(course);
        return course;
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        return courseRepository.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getActiveCourses() {
        return courseRepository.findAllActive();
    }

    public void updateCourse(int id, String courseName, String description, int durationInWeeks)
            throws EntityNotFoundException, InvalidInputException {
        Course course = findCourseById(id);
        InputValidator.validateNonEmptyString(courseName, "Course name");
        InputValidator.validateNonEmptyString(description, "Description");
        InputValidator.validatePositiveInteger(durationInWeeks);

        course.setCourseName(courseName);
        course.setDescription(description);
        course.setDurationInWeeks(durationInWeeks);
        courseRepository.update(course);
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(false);
        courseRepository.update(course);
    }

    public void activateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(true);
        courseRepository.update(course);
    }

    public void removeCourse(int id) throws EntityNotFoundException {
        courseRepository.delete(id);
    }
}
