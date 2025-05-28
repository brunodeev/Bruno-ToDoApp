package com.bruno.dao;

import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.database.DbInitializer;
import com.bruno.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskDaoTest {
    TaskDao taskDao;

    @BeforeEach
    void init() {
        taskDao = new TaskDaoImpl();
        DbInitializer.createDatabases();
    }

    @Test
    void shouldAddTask() {
        var task = new Task(null, "Fazer tarefa", false);
        var id = taskDao.addTask(task);
        Assertions.assertNotNull(taskDao.getTaskById(id));
    }
}
