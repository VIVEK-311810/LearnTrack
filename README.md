# LearnTrack

## Brief

### 1. Project Summary
LearnTrack is a console-based Student & Course Management System built using Core Java.  
It will allow admins to manage:

- Students
- Courses
- Enrollments

The goal is to practice:

- Java basics (variables, data types, control flow)
- Classes, objects, constructors
- Static vs instance members
- OOP principles (encapsulation, basic inheritance, polymorphism)
- Collections (ArrayList)
- Basic exception handling
- Clean, readable code and modular design

This project is intentionally kept focused on fundamentals without advanced topics like concurrency, streams, or heavy design patterns. Those can be added later as the cohort progresses.

---

### 2. Learning Objectives
By completing LearnTrack, mentees should be comfortable with:

#### 2.1 Java setup & compilation
- Using JDK, understanding JDK vs JRE vs JVM at a basic level
- Compiling and running Java files via IDE and/or terminal

#### 2.2 Core Java Syntax & Basics
- Packages, classes, main method
- Data types (primitives vs reference types)
- Variables and their scopes (local, instance, static)
- Typecasting basics

#### 2.3 OOP Fundamentals
- Creating classes and objects
- Constructors (default, parameterized, constructor overloading)
- Encapsulation using private fields + getters/setters
- Basic inheritance and method overriding
- Simple polymorphism examples

#### 2.4 Logic & Control Flow
- if/else, switch, loops (for, while, do-while)
- Using conditions and loops in menu-driven console applications

#### 2.5 Collections
- Array vs ArrayList (why ArrayList is preferred for dynamic data)
- Storing and retrieving objects using ArrayList

#### 2.6 Exception Handling (Basic)
- try-catch blocks
- Handling invalid input, e.g., wrong menu option, empty lists

#### 2.7 Clean Code Mindset
- Small functions
- Clear method names
- Separation of concerns (entity vs service vs UI)

---

### 3. Project Requirements & Suggested Marks Breakdown (Total 100)

#### A. Environment Setup & JVM Understanding (10 marks)
1. Install and configure Java (JDK).
2. Create a file: `docs/Setup_Instructions.md` with:
   - JDK version used
   - Screenshots or brief explanation of “Hello World” program run.
3. Create `docs/JVM_Basics.md` explaining in simple language:
   - What is JDK, JRE, JVM
   - What is bytecode
   - What does “write once, run anywhere” mean (1–2 short paragraphs)

#### B. Package Structure & Basics (10 marks)
1. Base package (example):  
   `com.airtribe.learntrack`
2. Recommended sub-packages:
   - `entity` – Student, Course, Enrollment
   - `service` – StudentService, CourseService, EnrollmentService
   - `ui` – Menu / Console UI (Main.java)
   - `exception` – Custom exceptions (e.g., EntityNotFoundException, InvalidInputException)
   - `util` – Simple helper classes (e.g., IdGenerator, InputValidator)
   - `docs` – Documentation files
3. You must demonstrate:
   - Proper use of packages
   - `public`, `private`, and default access where appropriate
   - Use of static variables/methods where it makes sense (e.g., ID counters)

#### C. Core OOP Implementation (40 marks)

##### C.1 Entities & Encapsulation (15 marks)
Create these core entity classes:

1. **Student**
   - Fields: `id`, `firstName`, `lastName`, `email`, `batch`, `active` (boolean)

2. **Course**
   - Fields: `id`, `courseName`, `description`, `durationInWeeks`, `active`

3. **Enrollment**
   - Fields: `id`, `studentId`, `courseId`, `enrollmentDate`, `status`  
     (e.g., `"ACTIVE"`, `"COMPLETED"`, `"CANCELLED"` as String or simple enum if you want slightly advanced)

Requirements:
- Use private fields + public getters/setters (Encapsulation)
- Add parameterized constructors (and default constructor if needed)
- Demonstrate constructor overloading in at least one class (e.g., Student with and without email)

##### C.2 Inheritance & Basic Polymorphism (10 marks)
Introduce a small hierarchy to practice:

- **Person** (base class)
  - Fields: `id`, `firstName`, `lastName`, `email`
- **Student extends Person**
- Optionally, introduce **Trainer extends Person** (even if not used much) to show inheritance

Show:
- Use of `super` in constructors
- Simple method overriding, e.g., `getDisplayName()` in Person and specialized behavior in Student or Trainer

##### C.3 Static, Methods, and Utility Classes (15 marks)
1. Use an `IdGenerator` utility:
   - Static field like `private static int studentIdCounter`
   - Static methods like `getNextStudentId()`, `getNextCourseId()`
2. Create meaningful methods:
   - In services: `addStudent`, `removeStudent`, `updateStudent`, `listStudents`
   - Use methods with different parameters (overloading example)

#### D. Application Logic & Menu-Driven Console UI (25 marks)
The core of the project is a menu-based console application in `Main.java`.

Example features:

1. **Student Management**
   - Add new student
   - View all students
   - Search student by ID
   - Deactivate a student (set `active = false` instead of deleting)

2. **Course Management**
   - Add new course
   - View all courses
   - Activate/Deactivate a course

3. **Enrollment Management**
   - Enroll a student in a course
   - View enrollments for a student
   - Mark enrollment as completed/cancelled

Implementation expectations:
- Use `ArrayList` to store Students, Courses, and Enrollments.
- All data can be in-memory only (no file DB needed for now).
- Use loops and conditionals to show and process menu options.
- Handle invalid input gracefully:
  - Option not found
  - Non-existent student/course IDs
- Keep logic in service classes, and keep Main focused on:
  - Displaying menu
  - Reading input
  - Calling service methods

#### E. Basic Exception Handling (10 marks)
- Create at least one custom exception, for example:
  - `EntityNotFoundException` when a Student or Course is not found.
- Use try-catch around input parsing (e.g., converting String to int) and menu operations.
- Show a clean user message instead of crashing the program.

#### F. Documentation & Clean Code (5 marks)
1. `README.md` with:
   - Project description
   - How to compile and run
2. `docs/Design_Notes.md` answering:
   - Why you used `ArrayList` instead of array
   - Where you used static members and why
   - Where you used inheritance and what you gained from it
3. Follow simple Clean Code ideas:
   - Methods not too long
   - Meaningful names (`addStudent`, `findCourseById`, not `fun1` or `doWork`)

---

## How to Compile and Run

1. Open terminal in project root.
2. Compile:
   ```bash
   javac -d out $(find . -name "*.java")
   ```
   > On Windows (PowerShell), compile command may differ.
3. Run:
   ```bash
   java -cp out com.airtribe.learntrack.ui.Main
   ```
4. If your package/class name is different, update the run command accordingly.
