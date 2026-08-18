# 🎓 Student Management System

A full-stack **Student Management System** built with **Spring Boot**, **Spring Data JPA**, **Spring Security (JWT Authentication)**, **MySQL**, and a responsive **HTML, CSS, and JavaScript** frontend. The application allows administrators to securely manage students, track attendance, and perform CRUD operations through a clean web interface.

## 🌐 Live Demo

**Render Deployment:**
https://student-management-system-y8xu.onrender.com/

## 🚀 Features

* 👤 Secure Login System
* ➕ Add New Students
* 📝 Update Student Information
* ❌ Delete Students
* 📋 View All Students
* 📅 Attendance Management
* 🔍 Search and Manage Student Records
* 📱 Responsive User Interface
* ⚡ RESTful API Architecture
* 🗄️ MySQL/Postgres Database Integration
* 🌍 Deployed on Render
* 🗄️ DataBase Deployed on Neon Console Online
* 🐳 Dockerfile included for containerized deployment

## 🛠️ Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT Authentication
* Maven
* Tomcat

### Database

* MySQL
* Postgres

### Frontend

* HTML5
* CSS3
* JavaScript


### DevOps & Testing

🐳 Docker
🧪 Postman
Git & GitHub
Render (Cloud Deployment)


## 📂 Project Structure

```text
src/
 ├── main/
 │   ├── java/
 │   │    └── com/example/
 │   │         ├── config/
 │   │         ├── controller/
 │   │         ├── dto/
 │   │         ├── entity/
 │   │         ├── exception/
 │   │         ├── repository/
 │   │         └── service/
 │   └── resources/
 │        ├── static/
 │        └── application.properties
```

## ⚙️ Installation

### 1. Clone the Repository

```bash
git clone [https://github.com/<your-username>/<your-repository>.git](https://github.com/LokeshG02/Student-management-system)
cd Student-management-system

```

### 2. Configure MySQL

Update the database configuration inside:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the Project

```bash
mvn spring-boot:run
```

or

```bash
./mvnw spring-boot:run
```

The application will start at:

```
http://localhost:8080
```



## 📌 Main Modules

* User Authentication
* Student Management
* Attendance Management
* REST APIs
* Exception Handling
* Security Configuration

## 📷 Screenshots

* Login Page
  <img width="1470" height="956" alt="Screenshot 2026-08-18 at 12 10 01 PM" src="https://github.com/user-attachments/assets/de90486b-dfab-4c5d-9f2a-c6bb4dc9306f" />

* Dashboard
  <img width="1470" height="956" alt="Screenshot 2026-08-18 at 12 10 42 PM" src="https://github.com/user-attachments/assets/2ea3e385-b595-4dc7-a60a-8d199e1e8d62" />

* Student List
  <img width="1470" height="956" alt="Screenshot 2026-08-18 at 12 11 23 PM" src="https://github.com/user-attachments/assets/307fd24b-7ed1-4ed2-a750-3cdf2d86f085" />

* Attendance Page
  <img width="1470" height="956" alt="Screenshot 2026-08-18 at 12 12 10 PM" src="https://github.com/user-attachments/assets/8e603853-5ca9-4ce8-98fb-b128df4b532a" />


## 🚀 Deployment

The application is successfully deployed on **Render**.

**Live URL:**

https://student-management-system-y8xu.onrender.com/

## 📖 Future Improvements

* Role-Based Access Control (Admin/Teacher/Student)
* Student Profile Photos
* Dashboard Analytics
* Email Notifications
* Export Student Records (PDF/Excel)
* Pagination & Filtering
* Unit & Integration Tests

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature-name
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push the branch

```bash
git push origin feature-name
```

5. Open a Pull Request

## 📄 License

This project is intended for educational and learning purposes.

---

⭐ If you found this project helpful, consider giving the repository a **Star** on GitHub!

## 👨‍💻 Author

**Lokesh Gupta**

- GitHub: [https://github.com/your-username](https://github.com/LokeshG02)
- LinkedIn: [https://linkedin.com/in/your-profile](https://www.linkedin.com/in/lokeshguptapro/)
