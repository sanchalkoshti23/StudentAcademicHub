
package studenthub.test;

import studenthub.model.Student;

public class studentValidationTest {

    public static void main(String[] args) {

        System.out.println("Testing Student Validation");

        // Test 1: Valid student
        try {
            Student s1 = new Student(
                    10,
                    "Sanchal",
                    "sanchal@gmail.com",
                    "KIT College",
                    "CSE",
                    3
            );

            System.out.println("Test 1 Passed: " + s1.getName());

        } catch (IllegalArgumentException e) {
            System.out.println("Test 1 Failed: " + e.getMessage());
        }

        // Test 2: Empty student name
        try {
            Student s2 = new Student(
                    11,
                    "",
                    "student@gmail.com",
                    "KIT College",
                    "CSE",
                    3
            );

            System.out.println("Test 2 Failed: Invalid name accepted");

        } catch (IllegalArgumentException e) {
            System.out.println("Test 2 Passed: " + e.getMessage());
        }

        // Test 3: Invalid email
        try {
            Student s3 = new Student(
                    12,
                    "Rahul",
                    "invalid-email",
                    "KIT College",
                    "CSE",
                    3
            );

            System.out.println("Test 3 Failed: Invalid email accepted");

        } catch (IllegalArgumentException e) {
            System.out.println("Test 3 Passed: " + e.getMessage());
        }

        // Test 4: Invalid year
        try {
            Student s4 = new Student(
                    13,
                    "Amit",
                    "amit@gmail.com",
                    "KIT College",
                    "CSE",
                    0
            );

            System.out.println("Test 4 Failed: Invalid year accepted");

        } catch (IllegalArgumentException e) {
            System.out.println("Test 4 Passed: " + e.getMessage());
        }

        System.out.println("Validation Testing Completed");
    }
}
