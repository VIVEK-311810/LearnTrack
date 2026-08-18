package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.enums.CourseStatus;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.TrainerRepository;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.TrainerService;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static TrainerService trainerService;
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
        TrainerRepository trainerRepository = new TrainerRepository();

        studentService = new StudentService(studentRepository);
        courseService = new CourseService(courseRepository);
        trainerService = new TrainerService(trainerRepository);
        enrollmentService = new EnrollmentService(enrollmentRepository, studentService, courseService);

        scanner = new Scanner(System.in);
    }

    private static void displayMenu() {
        System.out.println("\n========== " + AppConstants.APP_NAME + " ==========");
        System.out.println("Welcome to LearnTrack!\n");

        boolean running = true;
        while (running) {
            System.out.println(MenuOptions.STUDENT_MANAGEMENT + ". Manage Students");
            System.out.println(MenuOptions.COURSE_MANAGEMENT + ". Manage Courses");
            System.out.println(MenuOptions.ENROLLMENT_MANAGEMENT + ". Manage Enrollments");
            System.out.println("4. Manage Trainers");
            System.out.println("5. View All Students");
            System.out.println("6. View All Courses");
            System.out.println("7. View All Trainers");
            System.out.println("8. View All Enrollments");
            System.out.println("9. Exit");
            System.out.print("Select option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case MenuOptions.STUDENT_MANAGEMENT:
                        studentMenu();
                        break;
                    case MenuOptions.COURSE_MANAGEMENT:
                        courseMenu();
                        break;
                    case MenuOptions.ENROLLMENT_MANAGEMENT:
                        enrollmentMenu();
                        break;
                    case 4:
                        trainerMenu();
                        break;
                    case 5:
                        viewAllStudents();
                        break;
                    case 6:
                        viewAllCourses();
                        break;
                    case 7:
                        viewAllTrainers();
                        break;
                    case 8:
                        viewAllEnrollments();
                        break;
                    case 9:
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
        System.out.println(MenuOptions.ADD_STUDENT + ". Add Student");
        System.out.println(MenuOptions.SEARCH_STUDENT + ". Search Student by ID");
        System.out.println(MenuOptions.BACK_STUDENT + ". Back");
        System.out.print("Select: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case MenuOptions.ADD_STUDENT:
                    addStudent();
                    break;
                case MenuOptions.SEARCH_STUDENT:
                    searchStudent();
                    break;
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
        System.out.println(MenuOptions.ADD_COURSE + ". Add Course");
        System.out.println(MenuOptions.SEARCH_COURSE + ". Search Course by ID");
        System.out.println(MenuOptions.UPDATE_COURSE + ". Assign Trainer to Course");
        System.out.println(MenuOptions.BACK_COURSE + ". Back");
        System.out.print("Select: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case MenuOptions.ADD_COURSE:
                    addCourse();
                    break;
                case MenuOptions.SEARCH_COURSE:
                    searchCourse();
                    break;
                case MenuOptions.UPDATE_COURSE:
                    assignTrainerToCourse();
                    break;
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
            System.out.println("Status: " + course.getStatus());
            System.out.println("Trainer ID: " + (course.getTrainerId() == -1 ? "Not assigned" : course.getTrainerId()));
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_ID_FORMAT);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void assignTrainerToCourse() {
        try {
            System.out.print("Course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine());
            System.out.print("Trainer ID: ");
            int trainerId = Integer.parseInt(scanner.nextLine());

            Course course = courseService.findCourseById(courseId);
            Trainer trainer = trainerService.findTrainerById(trainerId);

            course.setTrainerId(trainerId);
            courseService.updateCourse(courseId, course.getCourseName(), course.getDescription(), course.getDurationInWeeks());

            System.out.println("Trainer " + trainer.getDisplayName() + " assigned to course " + course.getCourseName());
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void trainerMenu() {
        System.out.println("\n--- Trainer Management ---");
        System.out.println("1. Add Trainer");
        System.out.println("2. Search Trainer by ID");
        System.out.println("3. Back");
        System.out.print("Select: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addTrainer();
                    break;
                case 2:
                    searchTrainer();
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_INPUT);
        }
    }

    private static void addTrainer() {
        try {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Specialization: ");
            String specialization = scanner.nextLine();

            Trainer trainer = trainerService.addTrainer(firstName, lastName, email, specialization);
            System.out.println("Trainer added successfully. ID: " + trainer.getId());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchTrainer() {
        try {
            System.out.print("Enter Trainer ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Trainer trainer = trainerService.findTrainerById(id);
            System.out.println("\n--- Trainer Found ---");
            System.out.println("ID: " + trainer.getId());
            System.out.println("Name: " + trainer.getDisplayName());
            System.out.println("Email: " + trainer.getEmail());
            System.out.println("Specialization: " + trainer.getSpecialization());
            System.out.println("Active: " + trainer.isActive());
        } catch (NumberFormatException e) {
            System.out.println(AppConstants.INVALID_ID_FORMAT);
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void enrollmentMenu() {
        System.out.println("\n--- Enrollment Management ---");
        System.out.println(MenuOptions.ENROLL_STUDENT + ". Enroll Student in Course");
        System.out.println(MenuOptions.VIEW_ENROLLMENTS_STUDENT + ". View Enrollments for Student");
        System.out.println(MenuOptions.UPDATE_ENROLLMENT_STATUS + ". Update Enrollment Status");
        System.out.println(MenuOptions.BACK_ENROLLMENT + ". Back");
        System.out.print("Select: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case MenuOptions.ENROLL_STUDENT:
                    enrollStudent();
                    break;
                case MenuOptions.VIEW_ENROLLMENTS_STUDENT:
                    viewStudentEnrollments();
                    break;
                case MenuOptions.UPDATE_ENROLLMENT_STATUS:
                    updateEnrollmentStatus();
                    break;
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
                                 " | Duration: " + course.getDurationInWeeks() + " weeks | Status: " + course.getStatus() +
                                 " | Trainer: " + (course.getTrainerId() == -1 ? "Not assigned" : course.getTrainerId()));
            }
        }
    }

    private static void viewAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        if (trainers.isEmpty()) {
            System.out.println("\nNo trainers found.");
        } else {
            System.out.println("\n--- All Trainers ---");
            for (Trainer trainer : trainers) {
                System.out.println("ID: " + trainer.getId() + " | " + trainer.getDisplayName() +
                                 " | Specialization: " + trainer.getSpecialization() + " | Active: " + trainer.isActive());
            }
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
