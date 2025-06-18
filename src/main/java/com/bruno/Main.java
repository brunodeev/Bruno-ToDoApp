package com.bruno;

import com.bruno.config.RootConfig;
import com.bruno.config.ServletConfig;
import com.bruno.config.WebConfig;
import com.bruno.controller.MiniServlet;
import com.bruno.dao.TaskDao;
import com.bruno.database.DbInitializer;
import com.bruno.model.TaskHibernate;
import com.bruno.security.SecurityConfig;
import jakarta.servlet.DispatcherType;
import org.apache.wicket.protocol.http.WicketFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

public class Main {

    public static void main(String[] args) throws Exception {
        DbInitializer.createDatabases();



        AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
        rootContext.register(RootConfig.class, ServletConfig.class, SecurityConfig.class);
        rootContext.refresh();

        MiniServlet miniServlet = rootContext.getBean(MiniServlet.class);

        TaskDao taskDao = rootContext.getBean(TaskDao.class);

        taskDao.addTask(new TaskHibernate(null, "Tarefa Genérica 1", false));
        taskDao.addTask(new TaskHibernate(null, "Tarefa Genérica 2", false));

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        FilterHolder springSecurityFilter = new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain"));
        contextHandler.addFilter(springSecurityFilter, "/*", null);

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.register(WebConfig.class);

        webContext.setServletContext(contextHandler.getServletContext());
        webContext.refresh();

        contextHandler.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletHolder dispatcherServletHolder = new ServletHolder(dispatcherServlet);
        contextHandler.addServlet(dispatcherServletHolder, "/*");

        contextHandler.addServlet(new ServletHolder(miniServlet), "/custom-mvc/*");

        FilterHolder wicketFilterHolder = new FilterHolder(WicketFilter.class);
        wicketFilterHolder.setInitParameter("applicationClassName", "com.bruno.wicket.WicketApp");
        wicketFilterHolder.setInitParameter("filterMappingUrlPattern", "/wicket/*");
        wicketFilterHolder.setInitParameter("configuration", "development");
        contextHandler.addFilter(wicketFilterHolder, "/wicket/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();

        System.out.println("API RODANDO EM http://localhost:8080/");
        server.join();
    }
}
