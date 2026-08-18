package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository extends BaseRepository<Student> {
    public Student findById(int id) throws EntityNotFoundException {
        for (Student student : items) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student with ID " + id + " not found");
    }

    public List<Student> findAllActive() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : items) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
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
