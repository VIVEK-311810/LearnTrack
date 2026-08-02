# Design Notes: LearnTrack Architecture

## 1. Why ArrayList Instead of Arrays?

### Problem with Arrays
- **Fixed size:** Arrays have a fixed size determined at creation. If you declare `new Student[10]`, you can only store exactly 10 students.
- **No built-in methods:** Arrays don't have methods like `add()`, `remove()`, `contains()`. You must manually manage insertion and deletion.
- **Memory inefficiency:** If you allocate space for 100 students but only use 50, the other 50 slots are wasted.
- **Complexity:** Managing a manually-sized array requires tracking the number of elements, checking bounds, and shifting elements when adding/removing.

### Advantages of ArrayList
- **Dynamic size:** ArrayList grows automatically as you add elements. No need to pre-allocate space.
- **Built-in methods:** ArrayList provides convenient methods like `add()`, `remove()`, `get()`, `size()`, `contains()`, etc.
- **Memory efficient:** ArrayList only allocates space for elements it contains, plus some buffer for growth.
- **Type-safe:** Using generics (e.g., `List<Student>`) ensures type safety at compile time.
- **Iteration:** Easy iteration using for-each loops.

### Decision in LearnTrack
We use `ArrayList<Student>`, `ArrayList<Course>`, and `ArrayList<Enrollment>` in our service classes because:
1. The number of students, courses, and enrollments is not known in advance
2. We frequently need to add, remove, and search for entities
3. ArrayList provides a clean, object-oriented API for these operations
4. It reduces boilerplate code and makes the application more maintainable

**Code Example:**
```java
private List<Student> students = new ArrayList<>(); // Dynamic, grows as needed

students.add(student);           // Easy addition
students.remove(student);        // Easy removal
Student found = students.get(0); // Easy access
```

---

## 2. Use of Static Members and Why

### What are Static Members?
Static members (fields and methods) belong to the **class itself**, not to individual instances of the class. They are shared across all instances.

### Static Members in LearnTrack

#### IdGenerator Utility Class
```java
public class IdGenerator {
    private static int studentIdCounter = 1000;
    private static int courseIdCounter = 2000;
    private static int enrollmentIdCounter = 3000;

    public static int getNextStudentId() {
        return studentIdCounter++;
    }
    
    public static int getNextCourseId() {
        return courseIdCounter++;
    }
}
```

**Why we use static here:**
1. **Shared state:** We need ID counters that persist across all Student, Course, and Enrollment instances
2. **Single responsibility:** ID generation is not specific to any one object; it's a system-wide concern
3. **No instantiation needed:** We call `IdGenerator.getNextStudentId()` directly without creating an instance
4. **Uniqueness guarantee:** By using static counters, we ensure every entity gets a unique ID

**Without static:** Each Student instance would have its own counter, defeating the purpose.

#### Benefits:
- Centralized ID generation logic
- Guaranteed unique IDs across the application
- Clean API (no need to instantiate IdGenerator)
- Thread-safe under normal usage (though in a production system, you'd want synchronization)

---

## 3. Use of Inheritance and Polymorphism

### Inheritance Hierarchy
```
Person (base class)
  ↓
Student (extends Person)
```

### What We Gained

#### 1. Code Reuse
The `Person` class defines common properties (`id`, `firstName`, `lastName`, `email`) that any person-type entity might have. `Student` extends this without duplicating these fields.

```java
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}

public class Student extends Person {
    private String batch;
    private boolean active;
}
```

**Benefit:** If we later add a `Trainer` class, it can also extend `Person` and reuse the common fields.

#### 2. Method Overriding for Polymorphic Behavior
The `Person` class defines a `getDisplayName()` method:
```java
public class Person {
    public String getDisplayName() {
        return firstName + " " + lastName;
    }
}
```

The `Student` class overrides this for specialized behavior:
```java
public class Student extends Person {
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [" + batch + "]";
    }
}
```

**Benefit:** Code using a `Student` object can call `getDisplayName()` and get Student-specific formatting automatically. This is polymorphism in action.

#### 3. Extensibility
If we later add a `Trainer` class extending `Person`, any code that works with `Person` references will automatically work with both `Student` and `Trainer` instances (through polymorphism).

```java
public class Trainer extends Person {
    private String specialization;
    
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [" + specialization + "]";
    }
}
```

#### 4. Reduced Coupling
Services and UI code can refer to `Person` when they need common person data, without depending on specific subclasses.

---

## 4. Package Structure and Design

### Package Organization
```
com.airtribe.learntrack
├── entity       (Domain objects: Student, Course, Enrollment, Person)
├── service      (Business logic: StudentService, CourseService, EnrollmentService)
├── ui           (User interface: Main.java)
├── exception    (Custom exceptions: EntityNotFoundException, InvalidInputException)
├── util         (Utilities: IdGenerator, InputValidator)
└── docs         (Documentation)
```

### Design Principles Applied

#### Separation of Concerns
- **entity:** Represents data
- **service:** Contains business logic (CRUD operations, validations)
- **ui:** Handles user interaction and menu navigation
- **util:** Provides reusable helper functions
- **exception:** Defines custom exception types

#### Benefits:
1. **Maintainability:** Each package has a clear responsibility
2. **Testability:** Services can be tested independently from UI
3. **Scalability:** Easy to add new features without modifying existing code
4. **Reusability:** Services can be used by different UIs (console, web, mobile)

---

## 5. Exception Handling Strategy

### Custom Exceptions
We define two custom exceptions:

#### EntityNotFoundException
Thrown when a Student, Course, or Enrollment is not found by ID.
```java
public class EntityNotFoundException extends Exception { }
```

#### InvalidInputException
Thrown when user input fails validation (empty name, invalid email, etc.).
```java
public class InvalidInputException extends Exception { }
```

### Benefits
1. **Semantic clarity:** Callers know exactly what went wrong
2. **Graceful handling:** UI catches these exceptions and displays user-friendly messages
3. **Separation:** Business logic doesn't depend on UI error handling
4. **Robustness:** The application doesn't crash on invalid input

### Usage in Main.java
```java
try {
    Student student = studentService.addStudent(firstName, lastName, email, batch);
} catch (InvalidInputException e) {
    System.out.println("Error: " + e.getMessage());
}
```

---

## 6. Encapsulation with Getters and Setters

All entity classes use private fields with public getters and setters.

**Example:**
```java
public class Student extends Person {
    private String batch;
    private boolean active;
    
    public String getBatch() {
        return batch;
    }
    
    public void setBatch(String batch) {
        this.batch = batch;
    }
}
```

### Why?
1. **Data protection:** External code cannot directly modify internal state
2. **Validation:** Setters can add validation logic later without changing the API
3. **Flexibility:** Internal representation can change without affecting external code
4. **Consistency:** All access goes through controlled entry points

---

## Summary

The LearnTrack architecture demonstrates fundamental OOP principles:
- **Encapsulation:** Private fields with getters/setters
- **Inheritance:** Person base class with Student extending it
- **Polymorphism:** Method overriding in Student class
- **Separation of Concerns:** Organized packages for entity, service, UI, util, and exception
- **Collections:** ArrayList for dynamic data management
- **Exception Handling:** Custom exceptions for meaningful error reporting
- **Static Utilities:** IdGenerator for system-wide ID management

These design decisions make the codebase maintainable, testable, and easy to extend.
