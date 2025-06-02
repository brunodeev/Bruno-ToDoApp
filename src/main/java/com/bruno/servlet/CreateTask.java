package com.bruno.servlet;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Task;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CreateTask extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("templates/create.html");

        if (input == null) {
            response.setStatus(500);
            response.getWriter().write("Erro ao ler arquivo html!");
            return;
        }

        String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);

        response.setContentType("text/html");
        response.setStatus(200);
        response.getWriter().write(html);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TaskDao taskDao = new TaskDaoImpl();

        String name = request.getParameter("name");
        String concluded = request.getParameter("concluded");

        try {
            taskDao.addTask(new Task(null, name, Boolean.parseBoolean(concluded)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setStatus(200);
        response.sendRedirect("/list");
    }
}
