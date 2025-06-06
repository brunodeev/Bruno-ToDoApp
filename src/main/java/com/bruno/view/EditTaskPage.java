package com.bruno.view;

import com.bruno.annotation.Route;
import com.bruno.dao.TaskDao;
import com.bruno.dao.TaskDaoJdbc;
import com.bruno.model.Page;
import com.bruno.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Route("/edit")
public class EditTaskPage implements Page {
    TaskDao taskDao = new TaskDaoJdbc();

    @Override
    public String render(Map<String, Object> parameters) {

        if (parameters.containsKey("saveEdit")) {
            int id = Integer.parseInt((String) parameters.get("idEdit"));
            String name = (String) parameters.get("name");
            boolean concluded = Boolean.parseBoolean((String) parameters.get("concluded"));

            Task task = taskDao.getTaskById(id);
            if (task == null) {
                return "<meta http-equiv='refresh' content='0; URL=/not-found'>";
            }

            Task updated = new Task(id, name == null ? task.name() : name, concluded);
            taskDao.updateTask(updated);

            return "<meta http-equiv='refresh' content='0; URL=/list'>";
        }

        int id = Integer.parseInt((String) parameters.get("idEdit"));
        Task task = taskDao.getTaskById(id);

        if (task == null) {
            return "<meta http-equiv='refresh' content='0; URL=/not-found'>";
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
                .append("</select>")
                .append("<input type='hidden' name='saveEdit' value='true'>");

        html = html.replace("{{DATA}}", insert);

        return html;
    }
}
