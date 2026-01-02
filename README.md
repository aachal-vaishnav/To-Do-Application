# 📝 To-Do Application (Spring Boot)

A simple **To-Do List web application** built using **Spring Boot, Thymeleaf, Hibernate (JPA), and MySQL**.
This application allows users to **add, update, mark as complete, and delete tasks** with a clean Bootstrap-based UI.

---

## 🚀 Features

* Add new tasks
* View all tasks
* Update task content
* Mark tasks as complete
* Delete tasks
* Persistent storage using MySQL
* Clean MVC architecture

---

## 🛠️ Tech Stack

* **Backend:** Spring Boot, Spring MVC
* **ORM:** Hibernate / JPA
* **Frontend:** Thymeleaf, Bootstrap 5
* **Database:** MySQL
* **Build Tool:** Maven
* **Java Version:** Java 17+ (recommended)

---

## 📂 Project Structure

```
com.example.ToDo
│
├── controller
│   └── ToDoController.java
│
├── entity
│   └── ToDo.java
│
├── repository
│   └── TodoRepository.java
│
├── service
│   └── TodoService.java
│
├── ToDoApplication.java
│
resources
│
├── templates
│   └── task.html
│
└── application.yml
```

---

## ⚙️ Configuration

### Database Configuration (`application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/todo
    username: root
    password: YOUR_PASSWORD
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

> Make sure you have a MySQL database named **`todo`** created before running the application.

---

## ▶️ How to Run the Project

1. Clone the repository

   ```bash
   git clone https://github.com/aachal-vaishnav/To-Do-Application.git
   ```

2. Open the project in **IntelliJ IDEA / Eclipse**

3. Update database credentials in `application.yml`

4. Run the application

   ```bash
   mvn spring-boot:run
   ```

5. Open browser and visit

   ```
   http://localhost:8080
   ```

---

## 🌐 Application Endpoints

| Method | Endpoint           | Description    |
| ------ | ------------------ | -------------- |
| GET    | `/`                | View all tasks |
| POST   | `/addtodo`         | Add new task   |
| POST   | `/updatetodo/{id}` | Update task    |
| GET    | `/deleteToDo/{id}` | Delete task    |

---

## 📌 Future Enhancements

* User authentication
* Task due dates
* Task priority
* REST API version
* Pagination

---

## 👩‍💻 Author

**Aachal Vaishnav**
GitHub: [aachal-vaishnav](https://github.com/aachal-vaishnav)
