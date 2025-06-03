package com.bruno.controller;

import com.bruno.view.ListTasksPage;
import com.bruno.view.Page;
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

        if (path == null) path = "/list";

        Page page = switch (path) {
            case "/list" -> new ListTasksPage();
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
}
