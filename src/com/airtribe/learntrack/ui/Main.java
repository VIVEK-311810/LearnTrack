package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import java.util.Scanner;

public class Main {
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeServices();
        displayWelcome();
        scanner.close();
    }

    private static void initializeServices() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        studentService = new StudentService(studentRepository);
        courseService = new CourseService(courseRepository);
        enrollmentService = new EnrollmentService(enrollmentRepository, studentService, courseService);

        scanner = new Scanner(System.in);
    }

    private static void displayWelcome() {
        System.out.println("========== " + AppConstants.APP_NAME + " ==========");
        System.out.println("Welcome to LearnTrack!");
        System.out.println("Version: " + AppConstants.APP_VERSION);
        System.out.println("System initialized and ready to use.");
        System.out.println("All services configured successfully.");
    }
}
