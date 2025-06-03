package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Task;
import com.bruno.view.CreateTaskPage;
import com.bruno.view.ListTasksPage;
import com.bruno.model.Page;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MiniServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            response.sendRedirect("/list");
            return;
        }

        Page page = switch (path) {
            case "/list" -> new ListTasksPage();
            case "/create" -> new CreateTaskPage();
            default -> null;
        };

        if (page == null) {
            response.setStatus(404);
            response.getWriter().write("<h1>Página não encontrada</h1>");
            return;
        }

        Map<String, Object> parameters = new HashMap<>();

        for (String param : request.getParameterMap().keySet()) {
            parameters.put(param, request.getParameter(param));
        }

        String html = page.render(parameters);
        response.setContentType("text/html");
        response.getWriter().write(html);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TaskDao taskDao = new TaskDaoImpl();
        String path = request.getPathInfo();

        switch (path) {
            case "/create" -> {
                String name = request.getParameter("name");
                String concluded = request.getParameter("concluded");

                taskDao.addTask(new Task(null, name, Boolean.parseBoolean(concluded)));

                response.sendRedirect("/list");
            }
            case "/delete" -> {
                String id = request.getParameter("idDelete");
                taskDao.removeTaskById(Integer.parseInt(id));

                response.setStatus(200);
                response.sendRedirect("/list");
            }
            default -> {
                response.setStatus(404);
                response.getWriter().write("<h1>Ação não corresondente</h1>");
            }
        };
    }
}
