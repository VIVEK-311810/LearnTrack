package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private List<Course> courses;

    public CourseRepository() {
        this.courses = new ArrayList<>();
    }

    public void save(Course course) {
        courses.add(course);
    }

    public Course findById(int id) throws EntityNotFoundException {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        throw new EntityNotFoundException("Course with ID " + id + " not found");
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public List<Course> findAllActive() {
        List<Course> activeCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    public void update(Course course) throws EntityNotFoundException {
        Course existing = findById(course.getId());
        int index = courses.indexOf(existing);
        courses.set(index, course);
    }

    public void delete(int id) throws EntityNotFoundException {
        Course course = findById(id);
        courses.remove(course);
    }

    public boolean exists(int id) {
        try {
            findById(id);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
