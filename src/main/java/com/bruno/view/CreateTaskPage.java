package com.bruno.view;

import com.bruno.annotation.Route;
import com.bruno.dao.TaskDao;
import com.bruno.factory.BeanFactory;
import com.bruno.model.Page;
import com.bruno.model.TaskHibernate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Route("/create")
public class CreateTaskPage implements Page {

    TaskDao taskDao = BeanFactory.createTaskDao("HIBERNATE");

    @Override
    public String render(Map<String, Object> parameters) {

        if (parameters.containsKey("name")) {
            String name = (String)parameters.get("name");
            String concluded = (String)parameters.get("concluded");

            try {
                if (!name.isEmpty()) {
                    taskDao.addTask(new TaskHibernate(null, name, Boolean.parseBoolean(concluded)));
                    return "<meta http-equiv='refresh' content='0; URL=/custom-mvc/list'>";
                } else {
                    return "<meta http-equiv='refresh' content='0; URL=/custom-mvc/create'>";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "<meta http-equiv='refresh' content='0; URL=/custom-mvc/create'>";
            }
        }

        String html;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/custom/create.html")) {

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
