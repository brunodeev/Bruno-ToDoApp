package com.bruno.controller;

import com.bruno.annotation.Route;
import com.bruno.model.Page;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MiniServlet extends HttpServlet {

    private final Map<String, Page> pageRouter = new HashMap<>();

    @Override
    public void init() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.bruno.view").scan()) {
            scanResult.getClassesWithAnnotation(Route.class.getName()).forEach(classInfo -> {
                try {
                    Class<?> struct = classInfo.loadClass();
                    Route route = struct.getAnnotation(Route.class);

                    if (Page.class.isAssignableFrom(struct)) {
                        Page page = (Page) struct.getDeclaredConstructor().newInstance();
                        pageRouter.put(route.value(), page);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
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
