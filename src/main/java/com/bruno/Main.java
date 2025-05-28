package com.bruno;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.database.DbInitializer;
import com.bruno.model.Task;

public class Main {

    public static void main(String[] args) {
        DbInitializer.createDatabases();

        TaskDao taskDao = new TaskDaoImpl();

        taskDao.addTask(new Task(null, "Comprar pão", false));
        taskDao.addTask(new Task(null, "Comprar arroz", false));
        taskDao.addTask(new Task(null, "Comprar feijão", true));
        taskDao.addTask(new Task(null, "Comprar cebola", false));
        taskDao.addTask(new Task(null, "Comprar abóbora", false));

        taskDao.listAllTasks().forEach(i ->
                System.out.println(i.id() + " | " + i.name() + " | " + i.completed())
        );

        System.out.println();

        System.out.println(taskDao.getTaskById(2));
    }
}
