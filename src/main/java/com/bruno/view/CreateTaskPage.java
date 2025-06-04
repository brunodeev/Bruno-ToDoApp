package com.bruno.view;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Page;
import com.bruno.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CreateTaskPage implements Page {

    private final TaskDao taskDao = new TaskDaoImpl();

    @Override
    public String render(Map<String, Object> parameters) {

        if (parameters.containsKey("name")) {
            String name = (String)parameters.get("name");
            String concluded = (String)parameters.get("concluded");

            try {
                if (!name.isEmpty()) {
                    taskDao.addTask(new Task(null, name, Boolean.parseBoolean(concluded)));
                    return "<meta http-equiv='refresh' content='0; URL=/list'>";
                } else {
                    return "<meta http-equiv='refresh' content='0; URL=/create'>";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "<meta http-equiv='refresh' content='0; URL=/create'>";
            }
        }

        String html;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/create.html")) {

            if (input == null) {
                return null;
            }

            html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return html;
    }
}
