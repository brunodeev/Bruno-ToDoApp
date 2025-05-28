package com.bruno.dao;

import com.bruno.model.Task;

import java.util.List;

public interface TaskDao {

    Integer addTask(Task task);

    void removeTaskById(Integer id);

    Task getTaskById(Integer id);

    List<Task> listAllTasks();

}