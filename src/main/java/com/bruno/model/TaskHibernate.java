package com.bruno.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskHibernate implements Task{

    @Id
    private Integer id;

    private String name;

    private boolean completed;

    public TaskHibernate(){}

    public TaskHibernate(Integer id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
