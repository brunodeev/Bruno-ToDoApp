package com.bruno.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/index.html")) {
            if (input == null) {
                resp.setStatus(404);
                resp.getWriter().write("Página não encontrada.");
                return;
            }

            String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            resp.setContentType("text/html");
            resp.getWriter().write(html);
        }
    }
}
