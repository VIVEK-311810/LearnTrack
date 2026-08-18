package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EnrollmentService {
    private EnrollmentRepository repository;
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(EnrollmentRepository repository, StudentService studentService, CourseService courseService) {
        this.repository = repository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId, String dateStr) throws EntityNotFoundException, InvalidInputException {
        studentService.findStudentById(studentId);
        courseService.findCourseById(courseId);

        LocalDate enrollmentDate;
        try {
            enrollmentDate = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Use YYYY-MM-DD");
        }

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId, enrollmentDate, EnrollmentStatus.ACTIVE);
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

    public void updateEnrollmentStatus(int id, String statusStr) throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(id);
        EnrollmentStatus status = EnrollmentStatus.fromString(statusStr);
        if (status == null) {
            throw new InvalidInputException("Invalid status. Use ACTIVE, COMPLETED, or CANCELLED");
        }
        enrollment.setStatus(status);
        repository.update(enrollment);
    }

    public void completeEnrollment(int id) throws EntityNotFoundException, InvalidInputException {
        updateEnrollmentStatus(id, "COMPLETED");
    }

    public void cancelEnrollment(int id) throws EntityNotFoundException, InvalidInputException {
        updateEnrollmentStatus(id, "CANCELLED");
    }
}
