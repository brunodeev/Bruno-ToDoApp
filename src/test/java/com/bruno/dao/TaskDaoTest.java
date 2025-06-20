package com.bruno.dao;

import com.bruno.config.RootConfig;
import com.bruno.config.ServletConfig;
import com.bruno.database.DbInitializer;
import com.bruno.model.TaskHibernate;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TaskDaoTest {

    private static AnnotationConfigApplicationContext context;
    private TaskDao taskDao;

    @BeforeAll
    static void setupSpringContext() {
        context = new AnnotationConfigApplicationContext();
        context.register(RootConfig.class, ServletConfig.class);
        context.refresh();
    }

    @AfterAll
    static void closeContext() {
        context.close();
    }

    @BeforeEach
    void init() {
        DbInitializer.createDatabases();

        taskDao = context.getBean(TaskDao.class);
        taskDao.listAllTasks().forEach(task -> taskDao.removeTaskById(task.getId()));
    }

    @Test
    void shouldAddTask() {
        var task = new TaskHibernate(null, "Fazer tarefa", false);
        var id = taskDao.addTask(task);
        Assertions.assertNotNull(taskDao.getTaskById(id));
        taskDao.removeTaskById(id);
    }

    @Test
    void shouldRemoveTask() {
        var id = taskDao.addTask(new TaskHibernate(null, "Task a deletar", false));
        taskDao.removeTaskById(id);
        var tasks = taskDao.listAllTasks();
        Assertions.assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldGetTaskById() {
        var id = taskDao.addTask(new TaskHibernate(null, "Task a ser buscada", false));
        var task = taskDao.getTaskById(id);
        Assertions.assertEquals(id, task.getId());
        taskDao.removeTaskById(id);
    }

    @Test
    void shouldListAllTasks() {
        var id1 = taskDao.addTask(new TaskHibernate(null, "Listar tarefa 1", false));
        var id2 = taskDao.addTask(new TaskHibernate(null, "Listar tarefa 2", false));
        var tasks = taskDao.listAllTasks();
        Assertions.assertEquals(2, tasks.size());
        taskDao.removeTaskById(id1);
        taskDao.removeTaskById(id2);
    }

    @Test
    void shouldUpdateTasks() {
        var task1 = new TaskHibernate(null, "Task sem alteração", false);
        var id1 = taskDao.addTask(task1);

        var task2 = new TaskHibernate(id1, "Task alterada", true);
        taskDao.updateTask(task2);

        var updatedTask = taskDao.getTaskById(id1);

        Assertions.assertEquals("Task alterada", updatedTask.getName());
    }
}
