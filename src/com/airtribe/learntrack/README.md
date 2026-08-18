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

## How to Compile and Run

1. Open terminal in project root.
2. Compile:
   ```bash
   javac -d out $(find src -name "*.java")
   ```
3. Run:
   ```bash
   java -cp out com.airtribe.learntrack.ui.Main
   ```
