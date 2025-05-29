package com.bruno;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.database.DbInitializer;
import com.bruno.model.Task;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        DbInitializer.createDatabases();

        TaskDao taskDao = new TaskDaoImpl();

        taskDao.addTask(new Task(null, "Criar aplicação servlet em java", true));
        taskDao.addTask(new Task(null, "Aplicar html no endpoint", true));

        HttpServlet htmlServlet = new HttpServlet() {
            @Override
            public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
                List<Task> tasks = new ArrayList<>();

                try {
                    tasks = taskDao.listAllTasks();
                } catch (Exception e) {
                    response.setStatus(500);
                    response.getWriter().write("Internal Server Error!");
                }

                InputStream input = getClass().getClassLoader().getResourceAsStream("templates/index.html");

                if (input == null) {
                    response.setStatus(500);
                    response.getWriter().write("Erro ao ler arquivo html!");
                    return;
                }

                String html = new String(input.readAllBytes(), StandardCharsets.UTF_8);

                StringBuilder list = new StringBuilder();

                for (Task task : tasks) {
                    list.append("<li>")
                            .append(task.id())
                            .append(" | ")
                            .append(task.name())
                            .append(" | ")
                            .append(task.completed())
                            .append("</li>\n");
                }

                html = html.replace("{{TASKS}}", list);

                response.setContentType("text/html");
                response.setStatus(200);
                response.getWriter().write(html);
            }
        };

        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(htmlServlet), "/bruno/tasks");

        Server server = new Server(8080);
        server.setHandler(servletHandler);
        server.start();

        System.out.println("API RODANDO EM http://localhost:8080/api/bruno");
    }
}
