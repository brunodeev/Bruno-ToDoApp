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
        taskDao.removeTaskById(id);
    }

    @Test
    void shouldRemoveTask() {
        var id = taskDao.addTask(new Task(null, "Task a deletar", false));
        taskDao.removeTaskById(id);
        var tasks = taskDao.listAllTasks();
        Assertions.assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldGetTaskById() {
        var id = taskDao.addTask(new Task(null, "Task a ser buscada", false));
        var task = taskDao.getTaskById(id);
        Assertions.assertEquals(id, task.id());
    }

    @Test
    void shouldListAllTasks() {
        var id1 = taskDao.addTask(new Task(null, "Listar tarefa 1", false));
        var id2 = taskDao.addTask(new Task(null, "Listar tarefa 2", false));
        var tasks = taskDao.listAllTasks();
        Assertions.assertEquals(2, tasks.size());
        taskDao.removeTaskById(id1);
        taskDao.removeTaskById(id2);
    }

    @Test
    void shouldUpdateTasks() {
        var id1 = taskDao.addTask(new Task(null, "Task sem alteração", false));
        var updated = taskDao.updateTask(new Task(id1, "Task alterada", true));
        Assertions.assertTrue(updated);
    }
}
