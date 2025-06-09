package com.bruno;

import com.bruno.config.AppConfig;
import com.bruno.controller.MiniServlet;
import com.bruno.dao.TaskDao;
import com.bruno.dao.TaskDaoHibernate;
import com.bruno.database.DbInitializer;
import com.bruno.database.HibernateUtil;
import com.bruno.factory.BeanFactory;
import com.bruno.middleware.AuthFilter;
import com.bruno.model.TaskHibernate;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        DbInitializer.createDatabases();

        TaskDao taskDao = BeanFactory.createTaskDao("HIBERNATE");
        taskDao.addTask(new TaskHibernate(null, "Task teste", false));

        ServletContextHandler customMvcHandler = new ServletContextHandler();
        customMvcHandler.setContextPath("/custom-mvc");
        customMvcHandler.addServlet(new ServletHolder(new MiniServlet()), "/*");
        customMvcHandler.addFilter(AuthFilter.class, "/*", null);

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        ServletHolder springServletHolder = new ServletHolder(dispatcherServlet);

        ServletContextHandler springMvcHandler = new ServletContextHandler();
        springMvcHandler.setContextPath("/spring-mvc");
        springMvcHandler.addServlet(springServletHolder, "/*");
        springMvcHandler.addFilter(AuthFilter.class, "/*", null);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{customMvcHandler, springMvcHandler});

        Server server = new Server(8080);
        server.setHandler(contexts);
        server.start();

        System.out.println("API RODANDO EM http://localhost:8080");
        server.join();
    }
}
