# ToDo Application API

A RESTful API for managing todo items built with Spring Boot, JPA/Hibernate, and MySQL. This application provides a complete CRUD (Create, Read, Update, Delete) interface for todo management with a web interface powered by Thymeleaf and Bootstrap.

## 🚀 Features

- Create new todo items
- Retrieve all todo items
- Update existing todo items
- Delete todo items
- Mark todos as complete/incomplete
- MySQL database persistence
- RESTful API design

## 📋 Table of Contents

- [Base URL](#base-url)
- [API Endpoints](#api-endpoints)
- [Data Model](#data-model)
- [Postman Collection](#postman-collection)
- [Setup Instructions](#setup-instructions)
- [Technologies Used](#technologies-used)

## 🌐 Base URL

```
http://localhost:8080
```

## 📡 API Endpoints

### 1. Get All Todos

Retrieves all todo items from the database.

**Endpoint:** `GET /`

**Request:**
```http
GET http://localhost:8080/
```

**Response Example:**
```json
[
  {
    "id": 1,
    "toDoContent": "Complete project documentation",
    "isComplete": false
  },
  {
    "id": 2,
    "toDoContent": "Review pull requests",
    "isComplete": true
  }
]
```

**Status Codes:**
- `200 OK` - Successfully retrieved all todos
- `500 Internal Server Error` - Server error

---

### 2. Create Todo

Creates a new todo item in the database.

**Endpoint:** `POST /addtodo`

**Request:**
```http
POST http://localhost:8080/addtodo
Content-Type: application/x-www-form-urlencoded

toDoContent=Sample task
complete=false
```

**Request Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `toDoContent` | string | Yes | The content/description of the todo item |
| `complete` | boolean | Yes | Completion status (true/false) |

**cURL Example:**
```bash
curl -X POST http://localhost:8080/addtodo \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "toDoContent=Sample task&complete=false"
```

**Response:**
- Redirects to the home page with the newly created todo item

**Status Codes:**
- `200 OK` - Todo created successfully
- `400 Bad Request` - Invalid request parameters
- `500 Internal Server Error` - Server error

---

### 3. Update Todo

Updates an existing todo item by its ID.

**Endpoint:** `POST /updatetodo/{id}`

**Request:**
```http
POST http://localhost:8080/updatetodo/1
Content-Type: application/x-www-form-urlencoded

toDoContent=Updated task
complete=true
```

**Path Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | integer | Yes | The ID of the todo item to update |

**Request Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `toDoContent` | string | Yes | The updated content/description |
| `complete` | boolean | Yes | Updated completion status (true/false) |

**cURL Example:**
```bash
curl -X POST http://localhost:8080/updatetodo/1 \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "toDoContent=Updated task&complete=true"
```

**Response:**
- Redirects to the home page with the updated todo item

**Status Codes:**
- `200 OK` - Todo updated successfully
- `404 Not Found` - Todo with specified ID not found
- `400 Bad Request` - Invalid request parameters
- `500 Internal Server Error` - Server error

---

### 4. Delete Todo

Deletes a todo item by its ID.

**Endpoint:** `GET /deleteToDo/{id}`

**Request:**
```http
GET http://localhost:8080/deleteToDo/1
```

**Path Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | integer | Yes | The ID of the todo item to delete |

**cURL Example:**
```bash
curl -X GET http://localhost:8080/deleteToDo/1
```

**Response:**
- Redirects to the home page with the todo item removed

**Status Codes:**
- `200 OK` - Todo deleted successfully
- `404 Not Found` - Todo with specified ID not found
- `500 Internal Server Error` - Server error

---

## 📊 Data Model

### ToDo Entity

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Unique identifier (auto-generated) |
| `toDoContent` | String | The content/description of the todo item |
| `isComplete` | Boolean | Completion status of the todo item |

**Example:**
```json
{
  "id": 1,
  "toDoContent": "Complete project documentation",
  "isComplete": false
}
```

---

## 📮 Postman Collection

### Import the Collection

You can import the complete Postman collection to test all API endpoints:

1. **Download the collection** or use the Postman import feature
2. **Import into Postman:**
    - Open Postman
    - Click "Import" button
    - Select the collection file or paste the collection link
    - The collection will be added to your workspace

### Using the Collection

The collection includes all four endpoints pre-configured:

- ✅ **GET All Todos** - Retrieve all todo items
- ✅ **Create Todo** - Add a new todo item
- ✅ **Update Todo** - Modify an existing todo
- ✅ **Delete Todo** - Remove a todo item

### Running the Collection

**Option 1: Run Individual Requests**
1. Select any request from the collection
2. Click the "Send" button
3. View the response in the response panel

**Option 2: Run Entire Collection**
1. Click the three dots (•••) next to the collection name
2. Select "Run collection"
3. Configure run settings (iterations, delay, etc.)
4. Click "Run ToDo Application API"
5. View the test results and response times

**Note:** Make sure your Spring Boot application is running on `http://localhost:8080` before testing the endpoints.

---

## ⚙️ Setup Instructions

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

### Database Configuration

1. **Create MySQL Database:**
```sql
CREATE DATABASE todo_db;
```

2. **Configure Database Connection:**

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080
```

3. **Update Credentials:**
    - Replace `your_mysql_username` with your MySQL username
    - Replace `your_mysql_password` with your MySQL password

### Running the Application

1. **Clone the Repository:**
```bash
git clone <your-repository-url>
cd todo-application
```

2. **Build the Project:**
```bash
mvn clean install
```

3. **Run the Application:**
```bash
mvn spring-boot:run
```

Or run the JAR file directly:
```bash
java -jar target/todo-application-0.0.1-SNAPSHOT.jar
```

4. **Verify the Application:**
    - Open your browser and navigate to `http://localhost:8080`
    - You should see the ToDo application web interface

### Testing the API

**Using cURL:**
```bash
# Get all todos
curl http://localhost:8080/

# Create a new todo
curl -X POST http://localhost:8080/addtodo \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "toDoContent=Test task&complete=false"

# Update a todo
curl -X POST http://localhost:8080/updatetodo/1 \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "toDoContent=Updated task&complete=true"

# Delete a todo
curl http://localhost:8080/deleteToDo/1
```

**Using Postman:**
- Import the provided Postman collection
- Run the requests individually or as a collection
- Monitor responses and status codes

**Using the Web Interface:**
- Navigate to `http://localhost:8080` in your browser
- Use the web interface to create, update, and delete todos

---

## 🛠️ Technologies Used

- **Spring Boot** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM framework
- **MySQL** - Relational database
- **Thymeleaf** - Server-side template engine
- **Bootstrap** - Frontend styling
- **Maven** - Dependency management

---

## 📝 API Response Codes

| Status Code | Description |
|-------------|-------------|
| 200 OK | Request successful |
| 400 Bad Request | Invalid request parameters |
| 404 Not Found | Resource not found |
| 500 Internal Server Error | Server error occurred |

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 🔗 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Postman Documentation](https://learning.postman.com/)

---
