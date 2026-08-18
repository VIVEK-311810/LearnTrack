package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class CourseService {
    private CourseRepository repository;

    public CourseService() {
        this.repository = new CourseRepository();
    }

    public Course addCourse(String courseName, String description, int durationInWeeks) throws InvalidInputException {
        InputValidator.validateName(courseName, "Course name");
        InputValidator.validateName(description, "Description");
        InputValidator.validatePositiveNumber(durationInWeeks, "Duration");

        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName.trim(), description.trim(), durationInWeeks, true);
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

    public void updateCourse(int id, String courseName, String description, int durationInWeeks) throws EntityNotFoundException, InvalidInputException {
        Course course = findCourseById(id);

        if (courseName != null && !courseName.trim().isEmpty()) {
            course.setCourseName(courseName.trim());
        }
        if (description != null && !description.trim().isEmpty()) {
            course.setDescription(description.trim());
        }
        if (durationInWeeks > 0) {
            course.setDurationInWeeks(durationInWeeks);
        }

        repository.update(course);
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        setCourseActive(id, false);
    }

    public void activateCourse(int id) throws EntityNotFoundException {
        setCourseActive(id, true);
    }

    private void setCourseActive(int id, boolean active) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(active);
        repository.update(course);
    }
}
