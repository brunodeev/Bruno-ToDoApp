package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Task;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EditTask extends HttpServlet {
    TaskDao taskDao = new TaskDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("idEdit"));

        Task task = taskDao.getTaskById(id);

        String html;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/edit.html")) {

            if (input == null) {
                response.setStatus(500);
                response.getWriter().write("Erro ao ler arquivo html!");
                return;
            }

            html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }

        StringBuilder insert = new StringBuilder();

        insert.append("<input type='hidden' name='idEdit' value='")
                .append(task.id())
                .append("'><label for='name'>Nome</label><input id='name' name='name' value='")
                .append(task.name())
                .append("'><label for='concluded'>Concluído</label><select id='concluded' name='concluded'>")
                .append(task.completed() ? "<option value='false'>Não concluído</option>" : "<option value='false' selected>Não concluído</option>")
                .append(task.completed() ? "<option value='true' selected>Concluído</option>" : "<option value='true'>Concluído</option>")
                .append("</select>");

        html = html.replace("{{DATA}}", insert);

        response.setContentType("text/html");
        response.setStatus(200);
        response.getWriter().write(html);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
