package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {
    private List<Enrollment> enrollments;

    public EnrollmentRepository() {
        this.enrollments = new ArrayList<>();
    }

    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public Enrollment findById(int id) throws EntityNotFoundException {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        throw new EntityNotFoundException("Enrollment with ID " + id + " not found");
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                studentEnrollments.add(enrollment);
            }
        }
        return studentEnrollments;
    }

    public List<Enrollment> findByCourseId(int courseId) {
        List<Enrollment> courseEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                courseEnrollments.add(enrollment);
            }
        }
        return courseEnrollments;
    }

    public void update(Enrollment enrollment) throws EntityNotFoundException {
        Enrollment existing = findById(enrollment.getId());
        int index = enrollments.indexOf(existing);
        enrollments.set(index, enrollment);
    }

    public void delete(int id) throws EntityNotFoundException {
        Enrollment enrollment = findById(id);
        enrollments.remove(enrollment);
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
