package com.bruno;

import com.bruno.database.DbInitializer;
import com.bruno.controller.CreateTask;
import com.bruno.controller.DeleteTask;
import com.bruno.controller.EditTask;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

    public static void main(String[] args) throws Exception {
        DbInitializer.createDatabases();

        ServletContextHandler servletHandler = new ServletContextHandler();

        servletHandler.addServlet(new ServletHolder(new ListTasks()), "/");
        servletHandler.addServlet(new ServletHolder(new ListTasks()), "/list");
        servletHandler.addServlet(new ServletHolder(new CreateTask()), "/create");
        servletHandler.addServlet(new ServletHolder(new DeleteTask()), "/delete");
        servletHandler.addServlet(new ServletHolder(new EditTask()), "/edit");

        Server server = new Server(8080);
        server.setHandler(servletHandler);
        server.start();

        System.out.println("API RODANDO EM http://localhost:8080/list");
        server.join();
    }
}
