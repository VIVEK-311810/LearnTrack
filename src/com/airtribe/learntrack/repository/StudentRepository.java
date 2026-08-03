package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>();
    }

    public void save(Student student) {
        students.add(student);
    }

    public Student findById(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student with ID " + id + " not found");
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public List<Student> findAllActive() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    public void update(Student student) throws EntityNotFoundException {
        Student existing = findById(student.getId());
        int index = students.indexOf(existing);
        students.set(index, student);
    }

    public void delete(int id) throws EntityNotFoundException {
        Student student = findById(id);
        students.remove(student);
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
