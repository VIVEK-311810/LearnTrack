package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        InputValidator.validateName(firstName);
        InputValidator.validateName(lastName);
        InputValidator.validateEmail(email);
        InputValidator.validateNonEmptyString(batch, "Batch");

        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch, true);
        studentRepository.save(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch)
            throws InvalidInputException {
        InputValidator.validateName(firstName);
        InputValidator.validateName(lastName);
        InputValidator.validateNonEmptyString(batch, "Batch");

        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, batch, true);
        studentRepository.save(student);
        return student;
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getActiveStudents() {
        return studentRepository.findAllActive();
    }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(id);
        InputValidator.validateName(firstName);
        InputValidator.validateName(lastName);
        InputValidator.validateEmail(email);
        InputValidator.validateNonEmptyString(batch, "Batch");

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setBatch(batch);
        studentRepository.update(student);
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
        studentRepository.update(student);
    }

    public void activateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(true);
        studentRepository.update(student);
    }

    public void removeStudent(int id) throws EntityNotFoundException {
        studentRepository.delete(id);
    }
}
