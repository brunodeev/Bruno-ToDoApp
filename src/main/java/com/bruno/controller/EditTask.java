package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Task;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EditTask {
    TaskDao taskDao = new TaskDaoImpl();

    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("idEdit"));
        String name = request.getParameter("name");
        boolean concluded = Boolean.parseBoolean(request.getParameter("concluded"));

        Task tempTask = taskDao.getTaskById(id);

        Task updated = new Task(id, name == null ? tempTask.name() : name, concluded);

        taskDao.updateTask(updated);

        response.setStatus(200);
        response.sendRedirect("/list");
    }
}
