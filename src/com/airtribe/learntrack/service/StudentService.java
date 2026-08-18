package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class StudentService {
    private StudentRepository repository;

    public StudentService() {
        this.repository = new StudentRepository();
    }

    public Student addStudent(String firstName, String lastName, String email, String batch) throws InvalidInputException {
        InputValidator.validateName(firstName, "First name");
        InputValidator.validateName(lastName, "Last name");
        InputValidator.validateName(batch, "Batch");
        InputValidator.validateEmail(email);

        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName.trim(), lastName.trim(), email.trim(), batch.trim(), true);
        repository.save(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch) throws InvalidInputException {
        return addStudent(firstName, lastName, "", batch);
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public List<Student> getActiveStudents() {
        return repository.findAllActive();
    }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch) throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(id);

        if (firstName != null && !firstName.trim().isEmpty()) {
            student.setFirstName(firstName.trim());
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            student.setLastName(lastName.trim());
        }
        if (email != null && !email.trim().isEmpty()) {
            InputValidator.validateEmail(email);
            student.setEmail(email.trim());
        }
        if (batch != null && !batch.trim().isEmpty()) {
            student.setBatch(batch.trim());
        }

        repository.update(student);
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        setStudentActive(id, false);
    }

    public void activateStudent(int id) throws EntityNotFoundException {
        setStudentActive(id, true);
    }

    private void setStudentActive(int id, boolean active) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(active);
        repository.update(student);
    }
}
