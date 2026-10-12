package studenthub.test;

import java.util.Scanner;

import studenthub.dao.StudentDAO;
import studenthub.model.Student;

public class studentTest {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO dao = new StudentDAO();

        System.out.println("================================");
        System.out.println("    STUDENT MANAGEMENT SYSTEM");
        System.out.println("================================");

        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Find Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");

        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();

        switch (choice) {

        case 1:

            sc.nextLine(); // clear input buffer

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter College: ");
            String college = sc.nextLine();

            System.out.print("Enter Branch: ");
            String branch = sc.nextLine();

            System.out.print("Enter Year: ");
            int year = sc.nextInt();

            Student newStudent = new Student();

            newStudent.setName(name);
            newStudent.setEmail(email);
            newStudent.setCollege(college);
            newStudent.setBranch(branch);
            newStudent.setYear(year);

            dao.addStudent(newStudent);

            break;

            case 2:
                System.out.println("All Students:");
                dao.getAllStudents();
                break;

            case 3:
                System.out.print("Enter Student ID: ");

                int studentId = sc.nextInt();

                Student student = dao.getStudentById(studentId);

                if (student != null) {

                    System.out.println("----- Student Found -----");

                    System.out.println("Student ID: " + student.getStudentId());
                    System.out.println("Name: " + student.getName());
                    System.out.println("Email: " + student.getEmail());
                    System.out.println("College: " + student.getCollege());
                    System.out.println("Branch: " + student.getBranch());
                    System.out.println("Year: " + student.getYear());

                } else {

                    System.out.println("Student not found!");
                }

                break;

            case 4:

                System.out.print("Enter Student ID to update: ");
                int updateId = sc.nextInt();

                sc.nextLine();

                System.out.print("Enter New Name: ");
                String updateName = sc.nextLine();

                System.out.print("Enter New Email: ");
                String updateEmail = sc.nextLine();

                System.out.print("Enter New College: ");
                String updateCollege = sc.nextLine();

                System.out.print("Enter New Branch: ");
                String updateBranch = sc.nextLine();

                System.out.print("Enter New Year: ");
                int updateYear = sc.nextInt();

                Student updateStudent = new Student();

                updateStudent.setStudentId(updateId);
                updateStudent.setName(updateName);
                updateStudent.setEmail(updateEmail);
                updateStudent.setCollege(updateCollege);
                updateStudent.setBranch(updateBranch);
                updateStudent.setYear(updateYear);

                dao.updateStudent(updateStudent);

                break;

            case 5:

                System.out.print("Enter Student ID to delete: ");
                int deleteId = sc.nextInt();

                dao.deleteStudent(deleteId);

                break;

            case 6:
                System.out.println("Exit selected");
                break;

            default:
                System.out.println("Invalid choice");
        }

        sc.close();
    }
}