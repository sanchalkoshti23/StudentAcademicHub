# StudentAcademicHub 🎓

StudentAcademicHub is a Java-based student academic management application designed to help organize and manage essential academic information in one place.

The application uses Java Swing for its graphical user interface and MySQL for storing academic data, with JDBC connecting the application to the database.

## ✨ Features

- **Student Management:** Add, view, search, update, and delete student records.
- **Subject Management:** Manage subjects and their details.
- **Student Enrollment:** Associate students with subjects.
- **Attendance Management:** Record and view attendance information.
- **Marks Management:** Store and manage student marks.
- **Notes Management:** Organize academic notes.
- **Timetable Management:** Manage academic schedules.
- **Dashboard:** Access academic management features through the graphical interface.
- **Input Validation:** Validate user input and handle errors.

## 🛠️ Technologies Used

- **Programming Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **IDE:** Eclipse
- **Version Control:** Git and GitHub

## 📁 Project Structure

```text
StudentAcademicHub/
├── src/
│   └── studenthub/
│       ├── dao/       # Database operations
│       ├── gui/       # Graphical user interface
│       ├── model/     # Data model classes
│       ├── test/      # Test classes
│       ├── util/      # Database connection utilities
│       └── Main.java  # Application entry point
├── sql/
│   └── database.sql  # Database setup script
├── .gitignore
└── README.md
```

## ⚙️ Prerequisites

Before running the project, install:

- Java Development Kit (JDK)
- MySQL Server
- Eclipse IDE
- MySQL Connector/J

## 🚀 Setup and Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/sanchalkoshti23/StudentAcademicHub.git
   ```

2. Open the project in Eclipse.

3. Create the MySQL database and tables using the SQL script in the `sql/` folder.

4. Configure the database connection using environment variables:

   - `DB_USER`: Your MySQL username
   - `DB_PASSWORD`: Your MySQL password

5. Ensure MySQL Connector/J is available in the project's build path.

6. Run `Main.java` from Eclipse to launch the application.

## 🎯 Project Objective

The objective of StudentAcademicHub is to apply core software development concepts, including object-oriented programming, database connectivity, CRUD operations, input validation, and graphical user interface development, to a practical academic management system.

## 👨‍💻 Author

**Sanchal Koshti**

- GitHub: [sanchalkoshti23](https://github.com/sanchalkoshti23)

---

*Built as a hands-on project to strengthen Java programming and database management skills.*
