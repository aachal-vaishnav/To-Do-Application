# 📝 Spring Boot ToDo Application – Complete Learning Notes (From Scratch)

> This project is built as part of my **hands-on learning of Spring Framework**, focusing on **concept clarity, real flow understanding, and production-style layering**.
>
> Every line of code, annotation, configuration, and query is **intentionally written and deeply understood**, and this README documents **exactly what I learned and why**.

---

## 🚀 Tech Stack & Versions

* **Java**: v25
* **Spring Framework**: v4
* **Spring Boot**
* **Spring MVC**
* **Hibernate / JPA**
* **MySQL**
* **Thymeleaf**
* **Bootstrap (UI)**

---

## 📌 Project Overview

This is a **ToDo Application** that allows users to:

* Add tasks
* Update task content
* Mark tasks as complete / incomplete
* Delete tasks
* Persist data in **MySQL database**
* View tasks using **Thymeleaf templates**

---

## 🧠 High-Level Architecture (MVC Pattern)

```
Browser (HTML + Thymeleaf)
        ↓
Controller Layer (Spring MVC)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JPA / Hibernate)
        ↓
MySQL Database
```

---

## 📂 Package Structure & Responsibility

```
com.example.ToDo
│
├── controller   → Handles HTTP requests
├── service      → Business logic & validations
├── repository   → Database operations
├── entity       → Database table mapping
└── ToDoApplication.java → Entry point
```

---

## 🧩 Entity Layer – `ToDo`

```java
@Entity
@Table(name = "task")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String toDoContent;
    private boolean isComplete;
}
```

### 🔍 Explanation of Concepts

### `@Entity`

* Tells Hibernate: **“Create a table for this class”**
* Each object of `ToDo` = one row in DB

### `@Table(name = "task")`

* Forces table name to be `task`
* Without this → table name defaults from class name

### `@Id`

* Marks primary key

### `@GeneratedValue(strategy = GenerationType.AUTO)`

* Hibernate automatically generates ID
* In MySQL, Hibernate creates a **`task_seq` table** to simulate sequences

📌 **Why `task_seq` is created?**

* MySQL doesn’t support sequences natively
* Hibernate uses an extra table to manage IDs

> 💡 Using `GenerationType.IDENTITY` avoids sequence tables in MySQL.

---

## 🗄 Database Tables Created Automatically

| Table Name | Purpose                      |
| ---------- | ---------------------------- |
| `task`     | Stores todo data             |
| `task_seq` | Generates auto-increment IDs |

This happens because:

```yaml
spring.jpa.hibernate.ddl-auto: update
```

---

## 🧠 Repository Layer – Database Access

```java
@Repository
public class TodoRepository {

    @PersistenceContext
    private EntityManager entityManager;
```

### Why `@Repository`?

* Marks this class as DB access layer
* Enables exception translation

### Why `EntityManager`?

* Core JPA interface
* Gives low-level control over DB
* Helps understand Hibernate internally

---

### 🔹 Fetch All Todos

```java
public List<ToDo> findAll() {
    TypedQuery<ToDo> typedQuery =
        entityManager.createQuery("from ToDo", ToDo.class);
    return typedQuery.getResultList();
}
```

#### Concepts Explained:

* **HQL (Hibernate Query Language)** → operates on **entity names**, not table names
* `"from ToDo"` → means *select all ToDo entities*
* `TypedQuery<ToDo>` → type safety (no casting needed)

---

### 🔹 Find Todo by ID

```java
public Optional<ToDo> findTodoById(Long id) {
    ToDo todo = entityManager.find(ToDo.class, id);
    return Optional.ofNullable(todo);
}
```

#### Why `Optional<>`?

* Prevents `NullPointerException`
* Forces caller to handle absence safely

---

### 🔹 Save Todo

```java
@Transactional
public void save(ToDo todo) {
    entityManager.persist(todo);
}
```

### Why `@Transactional`?

> Ensures DB changes are committed on success and rolled back on failure.

---

### 🔹 Update Todo

```java
@Transactional
public void updateTodo(ToDo todo) {
    entityManager.merge(todo);
}
```

* `merge()` updates existing DB record
* Requires transaction

---

### 🔹 Delete Todo

```java
@Transactional
public void deleteTodoById(Long id) {
    ToDo todo = entityManager.find(ToDo.class, id);
    if (todo != null) {
        entityManager.remove(todo);
    }
}
```

---

## 🧠 Service Layer – Business Logic

```java
@Service
public class TodoService {
```

### Why Service Layer?

* Keeps controller thin
* Handles:

  * Business rules
  * Data transformation
  * Optional handling
  * Validation
* Follows **separation of concerns**

---

### Update Flow Example

```java
public void updateTodo(Long id, ToDo newTodo) {
    Optional<ToDo> oldTodoBox = todoRepository.findTodoById(id);

    if (oldTodoBox.isPresent()) {
        ToDo oldTodo = oldTodoBox.get();
        oldTodo.setToDoContent(newTodo.getToDoContent());
        oldTodo.setComplete(newTodo.isComplete());
        todoRepository.updateTodo(oldTodo);
    }
}
```

📌 This avoids:

* Overwriting ID
* Saving invalid objects
* Null pointer issues

---

## 🌐 Controller Layer – Request Handling

```java
@Controller
public class ToDoController {
```

### Why `@Controller`?

* Used for MVC + views (Thymeleaf)
* Returns HTML pages, not JSON

---

### 🔹 Get All Todos

```java
@RequestMapping("/")
public String getAllToDo(Model model) {
    List<ToDo> todos = todoService.getAllTodos();
    model.addAttribute("todoList", todos);
    return "task";
}
```

### `Model`

* Transfers data from Controller → View

---

### 🔹 Create Todo

```java
@RequestMapping(value = "/addtodo", method = RequestMethod.POST)
@ResponseBody
public String createToDo(@ModelAttribute ToDo todo) {
    todoService.saveTodo(todo);
    return "success";
}
```

### `@ModelAttribute`

* Automatically binds form fields to entity object
* Field names must match entity variable names

---

### 🔹 Update Todo

```java
@RequestMapping("/updatetodo/{id}")
public String updateToDo(@PathVariable Long id, @ModelAttribute ToDo todo) {
    todoService.updateTodo(id, todo);
    return "redirect:/";
}
```

### `@PathVariable`

* Extracts dynamic value from URL

---

### 🔹 Delete Todo

```java
@RequestMapping("/deleteToDo/{id}")
public String deleteToDo(@PathVariable Long id) {
    todoService.deleteTodo(id);
    return "redirect:/";
}
```

---

## 🎨 Thymeleaf View Layer

```html
<html xmlns:th="http://thymeleaf.org">
```

### Why Thymeleaf?

* Server-side rendering
* Seamless integration with Spring MVC

---

### Data Binding Example

```html
<input type="text"
       name="toDoContent"
       th:value="${todo.toDoContent}">
```

* `name` → binds to entity field
* `th:value` → shows DB value

---

### Checkbox Logic (Important Concept)

```html
<input type="checkbox"
       name="complete"
       th:checked="${todo.complete}"
       onchange="this.form.submit()">
```

📌 **Checkbox binding works using JavaBean property names**, not field names.

Setter:

```java
setComplete(boolean complete)
```

Property name = `complete`

---

## 🧾 Hibernate SQL Logs Explained

Example:

```sql
select td1_0.id, td1_0.is_complete, td1_0.to_do_content from task td1_0
```

* Hibernate auto-generates SQL from entity
* `td1_0` → alias
* Columns map to entity fields

Other logs:

```sql
select ... where id=?
delete from task where id=?
```

These correspond to:

* `find()`
* `remove()`
* `findAll()`

---

## ⚙️ Application Configuration (`application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/todo
    username: root
    password: ****
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### `ddl-auto` options explained:

| Option        | Meaning                          |
| ------------- | -------------------------------- |
| `update`      | Updates schema without data loss |
| `create`      | Drops & recreates tables         |
| `create-drop` | Deletes tables on shutdown       |
| `none`        | No schema changes                |

---

## 🔄 Live Reload (DevTools)

Normally:

* Server restart required after changes

With **Spring Boot DevTools**:

* Automatic reload
* Faster development

---

## 🧪 Ways to Perform DB Operations in Spring

1. **EntityManager** (Core JPA / Hibernate)
2. **CrudRepository**
3. **JpaRepository**

This project intentionally uses **EntityManager** to understand internals deeply.

---

## 🏁 Final Thoughts

This project reflects:

* Strong understanding of **Spring MVC**
* Clear grasp of **Hibernate & JPA**
* Proper **layered architecture**
* Real-world **data flow clarity**
* Attention to **edge cases & internals**
