package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This Java program demonstrates database operations using JDBC (Java Database Connectivity).
 * It connects to a MySQL database, creates tables, inserts sample data, and queries the tables.
 * The database used is "student_registration," and it assumes a local MySQL server with the following credentials:
 * - Database URL: jdbc:mysql://localhost:3306/student_registration
 * - USERNAME: root
 * - PASSWORD: root
 *
 * The program performs the following tasks:
 * 1. Establish a database connection.
 * 2. Drop and create tables (Student, Course, Registration) with primary keys and foreign keys.
 * 3. Insert sample data into the tables.
 * 4. Query and print data from the tables (Students, Courses, Registrations).
 *
 */

public class StudentRegistration {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student_registration";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
	
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            // drop tables
            dropTables(statement);
            
            //create tables
            createTables(statement);

            // Insert sample data into the tables
            insertSampleData(statement);

            // Query 1: Get all students
            queryStudents(statement);

            // Query 2: Get all courses
            queryCourses(statement);

            // Query 3: Get all registrations
            queryRegistrations(statement);

            // Close resources
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropTables(Statement statement) throws SQLException {
        // Drop tables in the correct order to avoid foreign key constraints issues
        statement.executeUpdate("DROP TABLE IF EXISTS Registration");
        statement.executeUpdate("DROP TABLE IF EXISTS Course");
        statement.executeUpdate("DROP TABLE IF EXISTS Student");
    }

    private static void createTables(Statement statement) throws SQLException {
        // Create Student table
        statement.executeUpdate(
            "CREATE TABLE Student (" +
            "StudentId VARCHAR(10) PRIMARY KEY," +
            "FirstName VARCHAR(50)," +
            "LastName VARCHAR(50)," +
            "Location VARCHAR(100))"
        );

        // Create Course table
        statement.executeUpdate(
            "CREATE TABLE Course (" +
            "CourseId VARCHAR(10) PRIMARY KEY," +
            "CourseName VARCHAR(50)," +
            "CourseTitle VARCHAR(50))"
        );

        // Create Registration table
        statement.executeUpdate(
            "CREATE TABLE Registration (" +
            "RegistrationId VARCHAR(10) PRIMARY KEY," +
            "CourseId VARCHAR(10)," +
            "StudentId VARCHAR(10)," +
            "FOREIGN KEY (CourseId) REFERENCES Course(CourseId)," +
            "FOREIGN KEY (StudentId) REFERENCES Student(StudentId))"
        );
    }


    // Helper method to insert sample data into the tables
    private static void insertSampleData(Statement statement) throws SQLException {
        insertStudentsData(statement);
        insertCoursesData(statement);
        insertRegistrationsData(statement);
    }

    private static void insertStudentsData(Statement statement) throws SQLException {
        String[] students = {
            "('S1', 'John', 'Doe', 'New York')",
            "('S2', 'Jane', 'Smith', 'Los Angeles')",
            "('S3', 'Robert', 'Brown', 'Chicago')",
            "('S4', 'Linda', 'Johnson', 'Houston')",
            "('S5', 'Michael', 'Williams', 'Phoenix')",
            "('S6', 'Elizabeth', 'Jones', 'Philadelphia')",
            "('S7', 'David', 'Garcia', 'San Antonio')",
            "('S8', 'Sarah', 'Martinez', 'San Diego')",
            "('S9', 'Daniel', 'Rodriguez', 'Dallas')",
            "('S10', 'Emily', 'Taylor', 'San Jose')"
        };

        for (String student : students) {
            statement.executeUpdate("INSERT INTO Student (StudentId, FirstName, LastName, Location) VALUES " + student);
        }
    }

    private static void insertCoursesData(Statement statement) throws SQLException {
        String[] courses = {
            "('C1', 'Math', 'Algebra 101')",
            "('C2', 'Science', 'Biology 101')",
            "('C3', 'English', 'Literature 101')",
            "('C4', 'History', 'World History 101')"
        };

        for (String course : courses) {
            statement.executeUpdate("INSERT INTO Course (CourseId, CourseName, CourseTitle) VALUES " + course);
        }
    }
    
    private static void insertRegistrationsData(Statement statement) throws SQLException {
        String[] registrations = {
            "('R1', 'C1', 'S1')",  
            "('R2', 'C2', 'S2')",  
            "('R3', 'C3', 'S3')",  
            "('R4', 'C4', 'S4')",  
            "('R5', 'C1', 'S5')",
            "('R6', 'C2', 'S6')",  
            "('R7', 'C3', 'S7')",  
            "('R8', 'C4', 'S8')",  
            "('R9', 'C1', 'S9')",  
            "('R10', 'C2', 'S10')", 
            "('R11', 'C3', 'S1')", 
            "('R12', 'C4', 'S2')"   
        };

        for (String registration : registrations) {
            statement.executeUpdate("INSERT INTO Registration (RegistrationId, CourseId, StudentId) VALUES " + registration);
        }
    }
    
    // Helper method to query and print students
    private static void queryStudents(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Student");
        while (resultSet.next()) {
            String studentId = resultSet.getString("StudentId");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String location = resultSet.getString("Location");
            System.out.println("Student: " + studentId + ", " + firstName + " " + lastName + ", " + location);
        }
    }

    // Helper method to query and print courses
    private static void queryCourses(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Course");
        while (resultSet.next()) {
            String courseId = resultSet.getString("CourseId");
            String courseName = resultSet.getString("CourseName");
            String courseTitle = resultSet.getString("CourseTitle");
            System.out.println("Course: " + courseId + ", " + courseName + ", " + courseTitle);
        }
    }

    // Helper method to query and print registrations
    private static void queryRegistrations(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Registration");
        while (resultSet.next()) {
            String registrationId = resultSet.getString("RegistrationId");
            String courseId = resultSet.getString("CourseId");
            String studentId = resultSet.getString("StudentId");
            System.out.println("Registration: " + registrationId + ", Course: " + courseId + ", Student: " + studentId);
        }
    }
}
