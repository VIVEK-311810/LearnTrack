package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static Scanner scanner;

    public static void main(String[] args) {
        studentService = new StudentService();
        courseService = new CourseService();
        enrollmentService = new EnrollmentService(studentService, courseService);
        scanner = new Scanner(System.in);

        displayMainMenu();
    }

    private static void displayMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n========== LearnTrack Management System ==========");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment Management");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        studentManagementMenu();
                        break;
                    case 2:
                        courseManagementMenu();
                        break;
                    case 3:
                        enrollmentManagementMenu();
                        break;
                    case 4:
                        running = false;
                        System.out.println("Thank you for using LearnTrack. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void studentManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== Student Management ==========");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. View active students");
            System.out.println("4. Search student by ID");
            System.out.println("5. Update student");
            System.out.println("6. Deactivate student");
            System.out.println("7. Activate student");
            System.out.println("8. Back to main menu");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        viewActiveStudents();
                        break;
                    case 4:
                        searchStudentById();
                        break;
                    case 5:
                        updateStudent();
                        break;
                    case 6:
                        deactivateStudent();
                        break;
                    case 7:
                        activateStudent();
                        break;
                    case 8:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void addStudent() {
        try {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter email (optional, press Enter to skip): ");
            String email = scanner.nextLine();
            System.out.print("Enter batch: ");
            String batch = scanner.nextLine();

            Student student;
            if (email.isEmpty()) {
                student = studentService.addStudent(firstName, lastName, batch);
            } else {
                student = studentService.addStudent(firstName, lastName, email, batch);
            }
            System.out.println("Student added successfully. ID: " + student.getId());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\n========== All Students ==========");
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + " | Name: " + student.getDisplayName()
                        + " | Email: " + student.getEmail() + " | Active: " + student.isActive());
            }
        }
    }

    private static void viewActiveStudents() {
        List<Student> students = studentService.getActiveStudents();
        if (students.isEmpty()) {
            System.out.println("No active students found.");
        } else {
            System.out.println("\n========== Active Students ==========");
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + " | Name: " + student.getDisplayName()
                        + " | Email: " + student.getEmail());
            }
        }
    }

    private static void searchStudentById() {
        try {
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Student student = studentService.findStudentById(id);
            System.out.println("\n========== Student Details ==========");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getDisplayName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Batch: " + student.getBatch());
            System.out.println("Active: " + student.isActive());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        try {
            System.out.print("Enter student ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            Student student = studentService.findStudentById(id);

            System.out.print("Enter new first name (" + student.getFirstName() + "): ");
            String firstName = scanner.nextLine();
            if (firstName.isEmpty())
                firstName = student.getFirstName();

            System.out.print("Enter new last name (" + student.getLastName() + "): ");
            String lastName = scanner.nextLine();
            if (lastName.isEmpty())
                lastName = student.getLastName();

            System.out.print("Enter new email (" + student.getEmail() + "): ");
            String email = scanner.nextLine();
            if (email.isEmpty())
                email = student.getEmail();

            System.out.print("Enter new batch (" + student.getBatch() + "): ");
            String batch = scanner.nextLine();
            if (batch.isEmpty())
                batch = student.getBatch();

            studentService.updateStudent(id, firstName, lastName, email, batch);
            System.out.println("Student updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deactivateStudent() {
        try {
            System.out.print("Enter student ID to deactivate: ");
            int id = Integer.parseInt(scanner.nextLine());
            studentService.deactivateStudent(id);
            System.out.println("Student deactivated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void activateStudent() {
        try {
            System.out.print("Enter student ID to activate: ");
            int id = Integer.parseInt(scanner.nextLine());
            studentService.activateStudent(id);
            System.out.println("Student activated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void courseManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== Course Management ==========");
            System.out.println("1. Add new course");
            System.out.println("2. View all courses");
            System.out.println("3. View active courses");
            System.out.println("4. Search course by ID");
            System.out.println("5. Update course");
            System.out.println("6. Deactivate course");
            System.out.println("7. Activate course");
            System.out.println("8. Back to main menu");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        viewAllCourses();
                        break;
                    case 3:
                        viewActiveCourses();
                        break;
                    case 4:
                        searchCourseById();
                        break;
                    case 5:
                        updateCourse();
                        break;
                    case 6:
                        deactivateCourse();
                        break;
                    case 7:
                        activateCourse();
                        break;
                    case 8:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void addCourse() {
        try {
            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            System.out.print("Enter duration (weeks): ");
            int duration = Integer.parseInt(scanner.nextLine());

            Course course = courseService.addCourse(courseName, description, duration);
            System.out.println("Course added successfully. ID: " + course.getId());
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration format.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("\n========== All Courses ==========");
            for (Course course : courses) {
                System.out.println("ID: " + course.getId() + " | Name: " + course.getCourseName()
                        + " | Duration: " + course.getDurationInWeeks() + " weeks | Active: " + course.isActive());
            }
        }
    }

    private static void viewActiveCourses() {
        List<Course> courses = courseService.getActiveCourses();
        if (courses.isEmpty()) {
            System.out.println("No active courses found.");
        } else {
            System.out.println("\n========== Active Courses ==========");
            for (Course course : courses) {
                System.out.println("ID: " + course.getId() + " | Name: " + course.getCourseName()
                        + " | Duration: " + course.getDurationInWeeks() + " weeks");
            }
        }
    }

    private static void searchCourseById() {
        try {
            System.out.print("Enter course ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Course course = courseService.findCourseById(id);
            System.out.println("\n========== Course Details ==========");
            System.out.println("ID: " + course.getId());
            System.out.println("Name: " + course.getCourseName());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Duration: " + course.getDurationInWeeks() + " weeks");
            System.out.println("Active: " + course.isActive());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateCourse() {
        try {
            System.out.print("Enter course ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            Course course = courseService.findCourseById(id);

            System.out.print("Enter new course name (" + course.getCourseName() + "): ");
            String courseName = scanner.nextLine();
            if (courseName.isEmpty())
                courseName = course.getCourseName();

            System.out.print("Enter new description (" + course.getDescription() + "): ");
            String description = scanner.nextLine();
            if (description.isEmpty())
                description = course.getDescription();

            System.out.print("Enter new duration in weeks (" + course.getDurationInWeeks() + "): ");
            String durationStr = scanner.nextLine();
            int duration = durationStr.isEmpty() ? course.getDurationInWeeks() : Integer.parseInt(durationStr);

            courseService.updateCourse(id, courseName, description, duration);
            System.out.println("Course updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deactivateCourse() {
        try {
            System.out.print("Enter course ID to deactivate: ");
            int id = Integer.parseInt(scanner.nextLine());
            courseService.deactivateCourse(id);
            System.out.println("Course deactivated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void activateCourse() {
        try {
            System.out.print("Enter course ID to activate: ");
            int id = Integer.parseInt(scanner.nextLine());
            courseService.activateCourse(id);
            System.out.println("Course activated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void enrollmentManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== Enrollment Management ==========");
            System.out.println("1. Enroll student in course");
            System.out.println("2. View all enrollments");
            System.out.println("3. View enrollments for a student");
            System.out.println("4. View enrollments for a course");
            System.out.println("5. Update enrollment status");
            System.out.println("6. Complete enrollment");
            System.out.println("7. Cancel enrollment");
            System.out.println("8. Back to main menu");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        enrollStudent();
                        break;
                    case 2:
                        viewAllEnrollments();
                        break;
                    case 3:
                        viewEnrollmentsForStudent();
                        break;
                    case 4:
                        viewEnrollmentsForCourse();
                        break;
                    case 5:
                        updateEnrollmentStatus();
                        break;
                    case 6:
                        completeEnrollment();
                        break;
                    case 7:
                        cancelEnrollment();
                        break;
                    case 8:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void enrollStudent() {
        try {
            System.out.print("Enter student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter enrollment date (YYYY-MM-DD): ");
            String enrollmentDate = scanner.nextLine();

            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId, enrollmentDate);
            System.out.println("Student enrolled successfully. Enrollment ID: " + enrollment.getId());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            System.out.println("\n========== All Enrollments ==========");
            for (Enrollment enrollment : enrollments) {
                System.out.println("ID: " + enrollment.getId() + " | Student ID: " + enrollment.getStudentId()
                        + " | Course ID: " + enrollment.getCourseId() + " | Status: " + enrollment.getStatus());
            }
        }
    }

    private static void viewEnrollmentsForStudent() {
        try {
            System.out.print("Enter student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine());
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);

            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
            } else {
                System.out.println("\n========== Enrollments for Student " + studentId + " ==========");
                for (Enrollment enrollment : enrollments) {
                    System.out.println("ID: " + enrollment.getId() + " | Course ID: " + enrollment.getCourseId()
                            + " | Date: " + enrollment.getEnrollmentDate() + " | Status: " + enrollment.getStatus());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewEnrollmentsForCourse() {
        try {
            System.out.print("Enter course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine());
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsForCourse(courseId);

            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this course.");
            } else {
                System.out.println("\n========== Enrollments for Course " + courseId + " ==========");
                for (Enrollment enrollment : enrollments) {
                    System.out.println("ID: " + enrollment.getId() + " | Student ID: " + enrollment.getStudentId()
                            + " | Date: " + enrollment.getEnrollmentDate() + " | Status: " + enrollment.getStatus());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateEnrollmentStatus() {
        try {
            System.out.print("Enter enrollment ID: ");
            int enrollmentId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new status (ACTIVE/COMPLETED/CANCELLED): ");
            String status = scanner.nextLine();

            enrollmentService.updateEnrollmentStatus(enrollmentId, status);
            System.out.println("Enrollment status updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void completeEnrollment() {
        try {
            System.out.print("Enter enrollment ID: ");
            int enrollmentId = Integer.parseInt(scanner.nextLine());
            enrollmentService.completeEnrollment(enrollmentId);
            System.out.println("Enrollment marked as completed.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelEnrollment() {
        try {
            System.out.print("Enter enrollment ID: ");
            int enrollmentId = Integer.parseInt(scanner.nextLine());
            enrollmentService.cancelEnrollment(enrollmentId);
            System.out.println("Enrollment cancelled.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
