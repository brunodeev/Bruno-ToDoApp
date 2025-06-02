package com.bruno;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.database.DbInitializer;
import com.bruno.model.Task;
import com.bruno.servlet.ListTasks;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

    public static void main(String[] args) throws Exception {
        DbInitializer.createDatabases();

        TaskDao taskDao = new TaskDaoImpl();

        taskDao.addTask(new Task(null, "Criar aplicação servlet em java", true));
        taskDao.addTask(new Task(null, "Aplicar html no endpoint", true));



        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(new ListTasks()), "/list");

        Server server = new Server(8080);
        server.setHandler(servletHandler);
        server.start();

        System.out.println("API RODANDO EM http://localhost:8080/list");
    }
}
