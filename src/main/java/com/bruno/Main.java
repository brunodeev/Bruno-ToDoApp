package com.bruno;

import com.bruno.controller.MiniServlet;
import com.bruno.database.DbInitializer;
import com.bruno.middleware.AuthFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

    public static void main(String[] args) throws Exception {
        DbInitializer.createDatabases();

        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.setContextPath("/custom-mvc");
        servletHandler.addServlet(new ServletHolder(new MiniServlet()), "/*");
        servletHandler.addFilter(AuthFilter.class, "/*", null);

        Server server = new Server(8080);
        server.setHandler(servletHandler);
        server.start();

        System.out.println("API RODANDO EM http://localhost:8080");
        server.join();
    }
}
