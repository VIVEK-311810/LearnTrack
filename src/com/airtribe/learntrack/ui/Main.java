package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeServices();
        displayMenu();
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

    private static void displayMenu() {
        System.out.println("\n========== " + AppConstants.APP_NAME + " ==========");
        System.out.println("Welcome to LearnTrack!\n");

        boolean running = true;
        while (running) {
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Enrollments");
            System.out.println("4. View All Students");
            System.out.println("5. View All Courses");
            System.out.println("6. View All Enrollments");
            System.out.println("7. Exit");
            System.out.print("Select option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        studentMenu();
                        break;
                    case 2:
                        courseMenu();
                        break;
                    case 3:
                        enrollmentMenu();
                        break;
                    case 4:
                        viewAllStudents();
                        break;
                    case 5:
                        viewAllCourses();
                        break;
                    case 6:
                        viewAllEnrollments();
                        break;
                    case 7:
                        running = false;
                        System.out.println(AppConstants.GOODBYE_MESSAGE);
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION);
                }
            } catch (NumberFormatException e) {
                System.out.println(AppConstants.INVALID_INPUT);
            }
        }
    }

    private static void studentMenu() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add Student");
        System.out.println("2. Search Student by ID");
        System.out.println("3. Back");
        System.out.print("Select: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    searchStudent();
                    break;
                case 3:
                    break;
                default:
                    System.out.println(AppConstants.INVALID_OPTION);
            }
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        }
    }

    private static void addStudent() {
        try {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Batch: ");
            String batch = scanner.nextLine();

            Student student = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println(AppConstants.STUDENT_ADDED + student.getId());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Student student = studentService.findStudentById(id);
            System.out.println("\n--- Student Found ---");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getDisplayName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Batch: " + student.getBatch());
            System.out.println("Active: " + student.isActive());
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_ID_FORMAT);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void courseMenu() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add Course");
        System.out.println("2. Search Course by ID");
        System.out.println("3. Back");
        System.out.print("Select: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    searchCourse();
                    break;
                case 3:
                    break;
                default:
                    System.out.println(AppConstants.INVALID_OPTION);
            }
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        }
    }

    private static void addCourse() {
        try {
            System.out.print("Course Name: ");
            String courseName = scanner.nextLine();
            System.out.print("Description: ");
            String description = scanner.nextLine();
            System.out.print("Duration (weeks): ");
            int duration = Integer.parseInt(scanner.nextLine());

            Course course = courseService.addCourse(courseName, description, duration);
            System.out.println(AppConstants.COURSE_ADDED + course.getId());
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration format.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchCourse() {
        try {
            System.out.print("Enter Course ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Course course = courseService.findCourseById(id);
            System.out.println("\n--- Course Found ---");
            System.out.println("ID: " + course.getId());
            System.out.println("Name: " + course.getCourseName());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Duration: " + course.getDurationInWeeks() + " weeks");
            System.out.println("Active: " + course.isActive());
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_ID_FORMAT);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("\n" + AppConstants.NO_STUDENTS);
        } else {
            System.out.println("\n--- All Students ---");
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + " | " + student.getDisplayName() +
                                 " | Batch: " + student.getBatch() + " | Active: " + student.isActive());
            }
        }
    }

    private static void viewAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("\n" + AppConstants.NO_COURSES);
        } else {
            System.out.println("\n--- All Courses ---");
            for (Course course : courses) {
                System.out.println("ID: " + course.getId() + " | " + course.getCourseName() +
                                 " | Duration: " + course.getDurationInWeeks() + " weeks | Active: " + course.isActive());
            }
        }
    }

    private static void enrollmentMenu() {
        System.out.println("\n--- Enrollment Management ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. View Enrollments for Student");
        System.out.println("3. Update Enrollment Status");
        System.out.println("4. Back");
        System.out.print("Select: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    enrollStudent();
                    break;
                case 2:
                    viewStudentEnrollments();
                    break;
                case 3:
                    updateEnrollmentStatus();
                    break;
                case 4:
                    break;
                default:
                    System.out.println(AppConstants.INVALID_OPTION);
            }
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        }
    }

    private static void enrollStudent() {
        try {
            System.out.print("Student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine());
            System.out.print("Course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enrollment Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId, date);
            System.out.println(AppConstants.ENROLLMENT_ADDED + enrollment.getId());
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewStudentEnrollments() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine());
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);

            if (enrollments.isEmpty()) {
                System.out.println("\nNo enrollments found for this student.");
            } else {
                System.out.println("\n--- Enrollments for Student " + studentId + " ---");
                for (Enrollment enrollment : enrollments) {
                    System.out.println("ID: " + enrollment.getId() + " | Course ID: " + enrollment.getCourseId() +
                                     " | Date: " + enrollment.getEnrollmentDate() + " | Status: " + enrollment.getStatus());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateEnrollmentStatus() {
        try {
            System.out.print("Enrollment ID: ");
            int enrollmentId = Integer.parseInt(scanner.nextLine());
            System.out.print("New Status (ACTIVE/COMPLETED/CANCELLED): ");
            String status = scanner.nextLine();

            enrollmentService.updateEnrollmentStatus(enrollmentId, status);
            System.out.println(AppConstants.ENROLLMENT_UPDATED);
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("\n" + AppConstants.NO_ENROLLMENTS);
        } else {
            System.out.println("\n--- All Enrollments ---");
            for (Enrollment enrollment : enrollments) {
                System.out.println("ID: " + enrollment.getId() + " | Student ID: " + enrollment.getStudentId() +
                                 " | Course ID: " + enrollment.getCourseId() + " | Status: " + enrollment.getStatus());
            }
        }
    }
}
