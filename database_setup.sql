-- Student Management System Database Setup Script
-- Run this script in MySQL to create the database and table

-- Create the database
CREATE DATABASE IF NOT EXISTS student_db;

-- Use the database
USE student_db;

-- Create the students table
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    course VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data for testing
INSERT INTO students (name, email, phone, course) VALUES
('John Doe', 'john.doe@example.com', '1234567890', 'Computer Science'),
('Jane Smith', 'jane.smith@example.com', '0987654321', 'Information Technology'),
('Robert Johnson', 'robert.j@example.com', '5551234567', 'Software Engineering'),
('Emily Davis', 'emily.davis@example.com', '5559876543', 'Data Science'),
('Michael Brown', 'michael.b@example.com', '5555555555', 'Computer Science');

-- Query to verify data
SELECT * FROM students;
