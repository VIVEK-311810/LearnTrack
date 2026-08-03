package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentService studentService,
            CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId, String enrollmentDate)
            throws EntityNotFoundException, InvalidInputException {
        studentService.findStudentById(studentId);
        courseService.findCourseById(courseId);
        InputValidator.validateNonEmptyString(enrollmentDate, "Enrollment date");

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId, enrollmentDate, "ACTIVE");
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) throws EntityNotFoundException {
        studentService.findStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsForCourse(int courseId) throws EntityNotFoundException {
        courseService.findCourseById(courseId);
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public void updateEnrollmentStatus(int enrollmentId, String newStatus)
            throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        InputValidator.validateNonEmptyString(newStatus, "Status");
        enrollment.setStatus(newStatus);
        enrollmentRepository.update(enrollment);
    }

    public void cancelEnrollment(int enrollmentId) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        enrollment.setStatus("CANCELLED");
        enrollmentRepository.update(enrollment);
    }

    public void completeEnrollment(int enrollmentId) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        enrollment.setStatus("COMPLETED");
        enrollmentRepository.update(enrollment);
    }

    public void removeEnrollment(int enrollmentId) throws EntityNotFoundException {
        enrollmentRepository.delete(enrollmentId);
    }
}
