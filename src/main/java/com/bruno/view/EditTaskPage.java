package com.bruno.view;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Page;
import com.bruno.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EditTaskPage implements Page {
    TaskDao taskDao = new TaskDaoImpl();

    @Override
    public String render(Map<String, Object> parameters) {
        int id = Integer.parseInt((String) parameters.get("idEdit"));

        Task task = taskDao.getTaskById(id);

        if (task == null) {
            return new NotFoundPage().render(parameters);
        }

        String html;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/edit.html")) {

            if (input == null) {
                html = null;
                return html;
            }

            html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder insert = new StringBuilder();

        insert.append("<input type='hidden' name='idEdit' value='")
                .append(task.id())
                .append("'><label for='name'>Nome</label><input id='name' name='name' value='")
                .append(task.name())
                .append("'><label for='concluded'>Concluído</label><select id='concluded' name='concluded'>")
                .append(task.completed() ? "<option value='false'>Não concluído</option>" : "<option value='false' selected>Não concluído</option>")
                .append(task.completed() ? "<option value='true' selected>Concluído</option>" : "<option value='true'>Concluído</option>")
                .append("</select>");

        html = html.replace("{{DATA}}", insert);

        return html;
    }
}
