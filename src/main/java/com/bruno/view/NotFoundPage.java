package com.bruno.view;

import com.bruno.annotation.Route;
import com.bruno.model.Page;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Route("not-found")
public class NotFoundPage implements Page {

    @Override
    public String render(Map<String, Object> parameters) {

        String html;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/404.html")) {

            if (input == null) {
                html = null;
                return html;
            }

            html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return html;
    }
}
