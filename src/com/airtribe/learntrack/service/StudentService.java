package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students;

    public StudentService() {
        this.students = new ArrayList<>();
    }

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch, true);
        students.add(student);
        return student;
    }

    // Overloading
    public Student addStudent(String firstName, String lastName, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, batch, true);
        students.add(student);
        return student;
    }

    // public Student findStudentById(int id) {
    // for (Student student : students) {
    // if (student.getId() == id) {
    // return student;
    // }
    // }
    // throw new EntityNotFoundException("Student with ID " + id + " not found");
    // }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // public List<Student> getActiveStudents() {
    // List<Student> activeStudents = new ArrayList<>();
    // for (Student student : students) {
    // if (student.isActive()) {
    // activeStudents.add(student);
    // }
    // }
    // return activeStudents;
    // }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setEmail(email);
                student.setBatch(batch);
                return;
            }
        }
    }

    // public void deactivateStudent(int id) {
    // Student student = findStudentById(id);
    // student.setActive(false);
    // }

    // public void activateStudent(int id) {
    // Student student = findStudentById(id);
    // student.setActive(true);

    public void removeStudent(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) {
                students.remove(student);
                return;
            }
        }
    }
}
