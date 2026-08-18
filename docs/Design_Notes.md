# Design Notes: LearnTrack Architecture

## 1. Why ArrayList Instead of Arrays?

Arrays have a fixed size, so if we create `new Student[10]`, we can only store exactly 10 students. 

ArrayList grows automatically as we add more students, so we don't have to worry about the size. 

It's easier to add, remove, and find students using ArrayList's built-in methods like `add()`, `remove()`, and `contains()` for easy Management.

## 2. Use of Static Members (IdGenerator)

Static members belongs to the class not the instances we create from them. 

It is used in scenarios where the values of the class should be global and shouldn't reset for each new instance.

Ex: In our codebase we have COunters to Course, Student and Enrollment IDs

## 3. Use of Inheritance

We created a Person base class with common fields like `id`, `firstName`, `lastName`, and `email`. 

Trainer and Student extends Person, so it inherits all these fields without rewriting them. 

It helps us reducing the need to write redundant code that works same in different classes if their basic nature is same like Student and Trainer are Person so we can use Inheritance here.