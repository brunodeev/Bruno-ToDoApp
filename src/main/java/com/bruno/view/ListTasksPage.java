package com.bruno.view;

import com.bruno.annotation.Route;
import com.bruno.dao.TaskDao;
import com.bruno.model.Page;
import com.bruno.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@Route("/list")
public class ListTasksPage implements Page {

    private final TaskDao taskDao;

    public ListTasksPage(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public String render(Map<String, Object> parameters) {
        List<Task> tasks = taskDao.listAllTasks();

        String html;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("templates/custom/list.html")) {

            if (input == null) {
                html = null;
                return html;
            }

            html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder list = new StringBuilder();

        if (tasks.isEmpty()) {
            list.append("<span>Você não tem nenhuma tarefa... :(</span>");
            html = html.replace("{{TASKS}}", list);
        } else {
            list.append("<table><tr><th>ID</th><th>Descrição</th><th>Concluído</th><th></th><th></th></tr>");


            for (Task task : tasks) {
                list.append("<tr>")
                        .append("<td>").append(task.getId()).append("</td>")
                        .append("<td>").append(task.getName()).append("</td>")
                        .append("<td>").append(task.isCompleted() ? "Concluído" : "Não concluído").append("</td>")

                        .append("<td>")
                        .append("<form method='get' action='/custom-mvc/edit'>")
                        .append("<input type='hidden' name='idEdit' value='").append(task.getId()).append("'>")
                        .append("<button type='submit' id='edit'>Editar</button>")
                        .append("</form>")
                        .append("</td>")

                        .append("<td>")
                        .append("<form method='get' action='/custom-mvc/delete'>")
                        .append("<input type='hidden' name='idDelete' value='").append(task.getId()).append("'>")
                        .append("<button type='submit' id='delete'>Deletar</button>")
                        .append("</form>")
                        .append("</td>")

                        .append("</tr>");
            }

            list.append("</table>");

            html = html.replace("{{TASKS}}", list);
        }

        return html;
    }
}
