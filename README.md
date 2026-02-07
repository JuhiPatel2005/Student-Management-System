# Student Management System

A desktop CRUD application built with Java Swing and MySQL for efficient student records management.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-orange?style=for-the-badge)

## 📸 Screenshots

<img width="1230" height="742" alt="image" src="https://github.com/user-attachments/assets/b09dc058-8e8e-47ff-8f62-0e32dcb1b315" />
<img width="1226" height="740" alt="image" src="https://github.com/user-attachments/assets/957f02e4-e4ba-4aec-99de-0382e1935794" />
<img width="1225" height="734" alt="image" src="https://github.com/user-attachments/assets/d3097f26-de0d-4474-afbf-34f44f98546c" />
<img width="1230" height="737" alt="image" src="https://github.com/user-attachments/assets/46f9f6eb-bb28-415c-a7e6-89755d335394" />
<img width="1226" height="739" alt="image" src="https://github.com/user-attachments/assets/4f471d96-4c6d-499c-96f9-5e25eedb0e6a" />



## 🎯 Overview

The Student Management System is a comprehensive desktop application designed to streamline student data management. Built with Java Swing for an intuitive graphical interface and MySQL for robust data persistence, this application provides a complete solution for educational institutions to manage student records efficiently.

## ✨ Features

- **Create** - Add new student records with validation
- **Read** - View all students in an organized table format
- **Update** - Modify existing student information seamlessly
- **Delete** - Remove student records with confirmation dialogs
- **Search** - Quick search functionality across multiple fields (name, email, course)
- **Data Persistence** - MySQL database ensures data integrity and reliability
- **Input Validation** - Prevents invalid data entry and duplicate emails
- **User-Friendly Interface** - Clean, intuitive design with color-coded buttons
- **Auto-Generated IDs** - Database automatically assigns unique student identifiers

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java SE** | Core programming language |
| **Java Swing** | GUI framework for desktop interface |
| **JDBC** | Database connectivity |
| **MySQL** | Relational database management |
| **AWT** | Additional UI components |

## 🏗️ Architecture

```
┌─────────────────────────────────────┐
│     Presentation Layer (Swing)      │
│  - Forms, Tables, Dialogs           │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     Business Logic Layer            │
│  - CRUD Operations                  │
│  - Input Validation                 │
│  - Error Handling                   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     Data Access Layer (JDBC)        │
│  - Database Connections             │
│  - SQL Queries                      │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     Database (MySQL)                │
│  - students table                   │
└─────────────────────────────────────┘
```

## 📊 Database Schema

**Table: students**

```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    course VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (JDBC Driver)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/JuhiPatel2005/student-management-system.git
cd student-management-system
```

2. **Set up MySQL database**
```bash
mysql -u root -p < sql/database_setup.sql
```

Or manually create the database:
```sql
CREATE DATABASE student_db;
USE student_db;
-- Run the CREATE TABLE statement from database_setup.sql
```

3. **Configure database connection**

Edit `src/StudentManagementSystem.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

Or use `config/db.properties` with the Enhanced version:
```properties
db.url=jdbc:mysql://localhost:3306/student_db
db.user=root
db.password=your_password
```

4. **Download MySQL Connector/J**
- Download from [MySQL Official Site](https://dev.mysql.com/downloads/connector/j/)
- Place `mysql-connector-java-8.x.x.jar` in the `lib/` folder

### Running the Application

**Using Command Line:**

```bash
# Compile
javac -cp ".:lib/mysql-connector-java-8.x.x.jar" src/StudentManagementSystem.java

# Run (Linux/Mac)
java -cp ".:lib/mysql-connector-java-8.x.x.jar:src" StudentManagementSystem

# Run (Windows)
java -cp ".;lib/mysql-connector-java-8.x.x.jar;src" StudentManagementSystem
```

**Using IDE (Eclipse/IntelliJ):**

1. Import the project
2. Add `mysql-connector-java-8.x.x.jar` to build path
3. Run `StudentManagementSystem.java`

## 💻 Usage

### Adding a Student
1. Fill in student details in the form
2. Click **Add** button
3. Student appears in the table

### Updating a Student
1. Click on a student row in the table
2. Modify the information in the form
3. Click **Update** button

### Deleting a Student
1. Select a student from the table
2. Click **Delete** button
3. Confirm deletion in the dialog

### Searching Students
1. Enter search term in the search field
2. Click **Search** button
3. Results are filtered in the table
4. Click **Refresh** to view all students

## 📁 Project Structure

```
student-management-system/
│
├── src/
│   ├── StudentManagementSystem.java          # Main application
│   └── StudentManagementSystemEnhanced.java  # Enhanced version with better UI
│
├── config/
│   └── db.properties                         # Database configuration
│
├── sql/
│   └── database_setup.sql                    # Database schema and sample data
│
├── lib/
│   └── mysql-connector-java-8.x.x.jar       # JDBC driver (download separately)
│
└── README.md                                 # Project documentation
```

## 🔒 Security Features

- **PreparedStatement** - Prevents SQL injection attacks
- **Input Validation** - Validates required fields and data formats
- **Unique Constraints** - Prevents duplicate email addresses
- **Error Handling** - Graceful error messages for database failures

## 🎨 UI Components

- **Form Panel** - Input fields for student data
- **Table Panel** - JTable displaying all records
- **Search Panel** - Quick search functionality
- **Action Buttons** - Color-coded buttons for different operations
  - 🟢 Green: Add new records
  - 🔵 Blue: Update existing records
  - 🔴 Red: Delete records
  - ⚪ Gray: Clear form

## 🧪 Testing

The application has been tested for:
- ✅ CRUD operations functionality
- ✅ Input validation (empty fields, invalid emails)
- ✅ Duplicate email detection
- ✅ Search across multiple fields
- ✅ Database connection error handling
- ✅ Table refresh after operations

## 🚧 Known Limitations

- Single-user application (no concurrent access handling)
- No user authentication system
- Local database only (no cloud integration)
- No data export/import functionality
- No backup/restore feature

## 🔮 Future Enhancements

- [ ] Add user authentication and authorization
- [ ] Implement student photo uploads
- [ ] Export data to CSV/Excel
- [ ] Import students from files
- [ ] Advanced filtering and sorting options
- [ ] Student attendance tracking
- [ ] Grade management system
- [ ] Email notifications
- [ ] Generate PDF reports
- [ ] Multi-user support with role-based access
- [ ] Cloud database integration
- [ ] Dark mode theme

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

**Juhi Patel**

- Email: juhipatel2005@gmail.com
- LinkedIn: [linkedin.com/in/juhipatel2005](https://linkedin.com/in/juhipatel2005)
- GitHub: [@JuhiPatel2005](https://github.com/JuhiPatel2005)

## 🙏 Acknowledgments

- Java Swing documentation and community
- MySQL documentation
- Stack Overflow community for troubleshooting assistance

## 📞 Support

If you encounter any issues or have questions, please:
- Open an issue on GitHub
- Contact me via email

---

⭐ If you found this project helpful, please give it a star!

**Made with ❤️ using Java and MySQL**
