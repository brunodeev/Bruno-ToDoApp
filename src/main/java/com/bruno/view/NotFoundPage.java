package com.bruno.view;

import com.bruno.annotation.Route;
import com.bruno.model.Page;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Route("/custom-mvc/not-found")
public class NotFoundPage implements Page {

    @Override
    public String render(Map<String, Object> parameters) {

        String html;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/custom/404.html")) {

            if (input == null) return null;

            html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return html;
    }
}
