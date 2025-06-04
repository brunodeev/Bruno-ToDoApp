package com.bruno.controller;

import com.bruno.view.*;
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
            case "/edit" -> new EditTaskPage();
            case "/delete" -> new DeleteTaskPage();
            default -> new NotFoundPage();
        };

        Map<String, Object> parameters = new HashMap<>();

        for (String param : request.getParameterMap().keySet()) {
            parameters.put(param, request.getParameter(param));
        }

        String html = page.render(parameters);
        response.setContentType("text/html");
        response.getWriter().write(html);
    }
}
