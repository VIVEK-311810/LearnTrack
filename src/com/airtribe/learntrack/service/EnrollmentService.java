package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class EnrollmentService {
    private EnrollmentRepository repository;
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.repository = new EnrollmentRepository();
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId, String enrollmentDate) throws EntityNotFoundException, InvalidInputException {
        InputValidator.validateDate(enrollmentDate);
        studentService.findStudentById(studentId);
        courseService.findCourseById(courseId);

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId, enrollmentDate.trim(), "ACTIVE");
        repository.save(enrollment);
        return enrollment;
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    public List<Enrollment> getAllEnrollments() {
        return repository.findAll();
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) throws EntityNotFoundException {
        studentService.findStudentById(studentId);
        return repository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsForCourse(int courseId) throws EntityNotFoundException {
        courseService.findCourseById(courseId);
        return repository.findByCourseId(courseId);
    }

    public void updateEnrollmentStatus(int id, String status) throws EntityNotFoundException, InvalidInputException {
        InputValidator.validateEnrollmentStatus(status);
        Enrollment enrollment = findEnrollmentById(id);
        enrollment.setStatus(status.toUpperCase());
        repository.update(enrollment);
    }

    public void completeEnrollment(int id) throws EntityNotFoundException {
        updateEnrollmentStatus(id, "COMPLETED");
    }

    public void cancelEnrollment(int id) throws EntityNotFoundException {
        updateEnrollmentStatus(id, "CANCELLED");
    }
}
