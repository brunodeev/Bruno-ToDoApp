package com.bruno.controller;

import com.bruno.dao.TaskDaoJdbc;
import com.bruno.database.DbInitializer;
import com.bruno.model.Page;
import com.bruno.view.ListTasksPage;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MiniServletTest {

    static MiniServlet miniServlet;

    @BeforeAll
    static void setupServer() throws Exception {
        DbInitializer.createDatabases();
        TaskDaoJdbc taskDaoJdbc = new TaskDaoJdbc();
        List<Page> pages = List.of(new ListTasksPage(taskDaoJdbc));
        miniServlet = new MiniServlet(pages);

        Server server = new Server(8085);
        ServletContextHandler handler = new ServletContextHandler(server, "/custom-mvc");
        handler.addServlet(new ServletHolder(miniServlet), "/*");
        server.start();
    }

    @Test
    void shouldStartServer() throws IOException {
        URL url = new URL("http://localhost:8085/custom-mvc/list");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }
        reader.close();

        Assertions.assertEquals(200, responseCode);
        Assertions.assertTrue(responseBody.toString().contains("List"));
    }
}
