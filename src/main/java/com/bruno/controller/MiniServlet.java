package com.bruno.controller;

import com.bruno.annotation.Route;
import com.bruno.dao.TaskDao;
import com.bruno.model.Page;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MiniServlet extends HttpServlet {

    private final Map<String, Page> pageRouter = new HashMap<>();

    @Autowired
    public MiniServlet(List<Page> pages) {
        for (Page page : pages) {
            Route route = page.getClass().getAnnotation(Route.class);
            if (route != null) {
                pageRouter.put(route.value(), page);
            } else {
                System.err.println("Page sem annotation @Route");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            response.sendRedirect("/custom-mvc/list");
            return;
        }

        Page page = pageRouter.getOrDefault(path, null);

        if (page == null) page = pageRouter.get("/custom-mvc/not-found");

        Map<String, Object> parameters = new HashMap<>();

        for (String param : request.getParameterMap().keySet()) {
            parameters.put(param, request.getParameter(param));
        }

        String html = page.render(parameters);
        response.setContentType("text/html");
        response.getWriter().write(html);
    }
}
