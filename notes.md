# 📝 Full Notes – Spring Boot ToDo Application

## Table of Contents

1. [Entity Layer](#entity-layer-todo)
2. [Repository Layer](#repository-layer)
3. [Service Layer](#service-layer)
4. [Controller Layer](#controller-layer)
5. [View Layer – Thymeleaf](#view-layer-thymeleaf)
6. [MVC Flow & Method Flow](#mvc-flow--method-flow)
7. [Application Configuration](#application-configuration-applicationyml)
8. [Hibernate SQL Logs](#hibernate-sql-logs)
9. [Postman & API Testing](#postman--api-testing)
10. [Key Concepts & Annotations](#key-concepts--annotations)
11. [Final Thoughts](#final-thoughts)

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

#### `@Entity`

* Tells Hibernate: **“Create a table for this class”**
* Each object of `ToDo` = one row in DB

#### `@Table(name = "task")`

* Forces table name to `task`
* Without this → table name defaults from class name

#### `@Id`

* Marks primary key

#### `@GeneratedValue(strategy = GenerationType.AUTO)`

* Hibernate automatically generates ID
* MySQL: Hibernate creates **`task_seq` table** for sequences

> 💡 Use `GenerationType.IDENTITY` to avoid sequence tables in MySQL

---

### 🗄 Database Tables Created Automatically

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

* Core JPA interface for low-level DB access
* Works as Hibernate’s main interface internally

---

### 🔹 Fetch All Todos

```java
public List<ToDo> findAll() {
    TypedQuery<ToDo> typedQuery = entityManager.createQuery("from ToDo", ToDo.class);
    return typedQuery.getResultList();
}
```

* **HQL (Hibernate Query Language)** → uses entity names, not table names
* `"from ToDo"` → select all ToDo entities
* `TypedQuery<ToDo>` → type safety, no casting needed

---

### 🔹 Find Todo by ID

```java
public Optional<ToDo> findTodoById(Long id) {
    ToDo todo = entityManager.find(ToDo.class, id);
    return Optional.ofNullable(todo);
}
```

* `Optional<>` prevents null pointer exceptions
* Forces caller to handle absence safely

---

### 🔹 Save Todo

```java
@Transactional
public void save(ToDo todo) {
    entityManager.persist(todo);
}
```

* `@Transactional` ensures **commit/rollback** semantics

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

* Keeps **Controller thin**
* Handles:

    * Business rules
    * Data transformation
    * Optional handling
    * Validation
* Follows **Separation of Concerns**

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

* Avoids overwriting ID
* Prevents saving invalid objects
* Handles null safely

---

## 🌐 Controller Layer – Request Handling

```java
@Controller
public class ToDoController {
```

### Why `@Controller`?

* Used for MVC + views (Thymeleaf)
* Returns HTML pages (not JSON)

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

* **Model** → transfers data from Controller → View

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

* `@ModelAttribute` binds form fields automatically to entity object
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

* `@PathVariable` → extracts dynamic value from URL

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

## 🎨 View Layer – Thymeleaf

```html
<html xmlns:th="http://www.thymeleaf.org">
```

### Why Thymeleaf?

* Server-side rendering
* Seamless Spring MVC integration

---

### Data Binding Example

```html
<input type="text" name="toDoContent" th:value="${todo.toDoContent}">
```

* `name` → binds to entity field
* `th:value` → displays DB value

---

### Checkbox Logic

```html
<input type="checkbox"
       name="complete"
       th:checked="${todo.complete}"
       onchange="this.form.submit()">
```

* JavaBean property name **must match setter/getter**
* `setComplete(boolean complete)` → property = `complete`

---

## 🧩 MVC Flow

1. **Browser → Controller**

    * `/` → `getAllToDo()`
    * `/addtodo` → `createToDo()`
2. **Controller → Service**

    * Calls business logic (`TodoService`)
3. **Service → Repository**

    * Handles DB CRUD via `EntityManager`
4. **Repository → DB**

    * HQL / SQL executes on DB
5. **Controller → View**

    * Model sends data to Thymeleaf HTML

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

### `ddl-auto` Options

| Option        | Meaning                          |
| ------------- | -------------------------------- |
| `update`      | Updates schema without data loss |
| `create`      | Drops & recreates tables         |
| `create-drop` | Deletes tables on shutdown       |
| `none`        | No schema changes                |

---

## 🧪 Hibernate SQL Logs

```sql
select td1_0.id, td1_0.is_complete, td1_0.to_do_content from task td1_0
```

* Auto-generated from entity
* Columns map to entity fields
* Other logs:

    * `delete from task where id=?` → remove()
    * `select ... where id=?` → find()

---

## 🔄 Postman & API Testing

| Method | Endpoint           | Description    |
| ------ | ------------------ | -------------- |
| POST   | `/addtodo`         | Add a new task |
| POST   | `/updatetodo/{id}` | Update task    |
| GET    | `/`                | Get all tasks  |
| GET    | `/deleteToDo/{id}` | Delete a task  |

**Tips:**

* Use `x-www-form-urlencoded` for POST data
* Import `ToDo.postman_collection.json` to test

---

## 📝 Key Concepts & Annotations

| Annotation            | Layer      | Purpose                                 |
| --------------------- | ---------- | --------------------------------------- |
| `@Entity`             | Entity     | Marks class as table                    |
| `@Table`              | Entity     | Specify table name                      |
| `@Id`                 | Entity     | Primary key                             |
| `@GeneratedValue`     | Entity     | Auto ID generation                      |
| `@Repository`         | Repository | DB access layer + exception translation |
| `@PersistenceContext` | Repository | Injects `EntityManager`                 |
| `@Transactional`      | Repository | Ensures commit/rollback                 |
| `@Service`            | Service    | Business logic layer                    |
| `@Controller`         | Controller | Handles HTTP requests & returns views   |
| `@RequestMapping`     | Controller | Maps URL to method                      |
| `@ResponseBody`       | Controller | Return raw response                     |
| `@ModelAttribute`     | Controller | Binds form fields to object             |
| `@PathVariable`       | Controller | URL parameter mapping                   |

---

## 🏁 Final Thoughts

* Strong understanding of **Spring MVC architecture**
* Full grasp of **JPA & Hibernate internals**
* Clean **layered architecture**: Entity → Repository → Service → Controller → View
* Clear **method & code flow** for CRUD operations
* Ready for **Postman testing and documentation**