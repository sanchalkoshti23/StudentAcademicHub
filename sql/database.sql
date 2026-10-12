CREATE DATABASE IF NOT EXISTS student_academic_hub;

USE student_academic_hub;

--Students table
CREATE TABLE `students` (
   `student_id` int NOT NULL AUTO_INCREMENT,
   `name` varchar(100) NOT NULL,
   `email` varchar(100) NOT NULL,
   `college` varchar(150) DEFAULT NULL,
   `branch` varchar(100) DEFAULT NULL,
   `year` int DEFAULT NULL,
   PRIMARY KEY (`student_id`),
   UNIQUE KEY `email` (`email`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
 -- Subjects table
CREATE TABLE subjects (
    subject_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_name VARCHAR(100) NOT NULL,
    subject_code VARCHAR(20) UNIQUE NOT NULL,
    semester INT NOT NULL
);
-- Student-Subject relationship
CREATE TABLE student_subjects (
    student_id INT NOT NULL,
    subject_id INT NOT NULL,

    PRIMARY KEY (student_id, subject_id),

    FOREIGN KEY (student_id)
        REFERENCES students(student_id),

    FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id)
);
-- Attendance table
CREATE TABLE attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(10) NOT NULL,

    FOREIGN KEY (student_id)
        REFERENCES students(student_id),

    FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id)
);
-- Marks table
CREATE TABLE marks (
    mark_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    exam_type VARCHAR(50) NOT NULL,
    marks_obtained DECIMAL(5,2) NOT NULL,
    max_marks DECIMAL(5,2) NOT NULL,

    FOREIGN KEY (student_id)
        REFERENCES students(student_id),

    FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id)
);
-- Timetable table
CREATE TABLE timetable (
    timetable_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(50),

    FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id)
);
-- Notes table
CREATE TABLE notes (
    note_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    file_path VARCHAR(255),

    FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id)
);