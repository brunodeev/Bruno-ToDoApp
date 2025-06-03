package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Task;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CreateTask extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TaskDao taskDao = new TaskDaoImpl();

        String name = request.getParameter("name");
        String concluded = request.getParameter("concluded");

        try {
            if (!name.isEmpty()) {
                taskDao.addTask(new Task(null, name, Boolean.parseBoolean(concluded)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!name.isEmpty()) {
            response.setStatus(200);
            response.sendRedirect("/list");
        } else {
            response.setStatus(400);
            response.sendRedirect("/create");
        }
    }
}
