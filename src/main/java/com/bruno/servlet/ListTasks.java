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
import java.util.ArrayList;
import java.util.List;

public class ListTasks extends HttpServlet {
    TaskDao taskDao = new TaskDaoImpl();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Task> tasks = new ArrayList<>();

        try {
            tasks = taskDao.listAllTasks();
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("Internal Server Error!");
        }

        InputStream input = getClass().getClassLoader().getResourceAsStream("templates/list.html");

        if (input == null) {
            response.setStatus(500);
            response.getWriter().write("Erro ao ler arquivo html!");
            return;
        }

        String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);

        StringBuilder list = new StringBuilder();

        for (Task task : tasks) {
            list.append("<tr>")
                    .append("<td>")
                    .append(task.id())
                    .append("</td>")
                    .append("<td>")
                    .append(task.name())
                    .append("</td>")
                    .append("<td>")
                    .append(task.completed())
                    .append("</td>")
                    .append("</tr>");
        }

        html = html.replace("{{TASKS}}", list);

        response.setContentType("text/html");
        response.setStatus(200);
        response.getWriter().write(html);
    }
}
