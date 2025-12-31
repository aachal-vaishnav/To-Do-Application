package com.example.ToDo.entity;

import jakarta.persistence.*;

@Entity //let spring know to create table of this class
@Table(name = "task")// -> if you want different table name: value store in this database
public class ToDo {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String toDoContent;
    private boolean isComplete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToDoContent() {
        return toDoContent;
    }

    public void setToDoContent(String toDoContent) {
        this.toDoContent = toDoContent;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
