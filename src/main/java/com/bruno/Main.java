package com.bruno;

import com.bruno.config.RootConfig;
import com.bruno.config.ServletConfig;
import com.bruno.config.WebConfig;
import com.bruno.controller.MiniServlet;
import com.bruno.database.DbInitializer;
import com.bruno.security.SecurityConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        DbInitializer.createDatabases();

        AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
        rootContext.register(RootConfig.class, ServletConfig.class, SecurityConfig.class);
        rootContext.refresh();

        MiniServlet miniServlet = rootContext.getBean(MiniServlet.class);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        FilterHolder springSecurityFilter = new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain"));
        contextHandler.addFilter(springSecurityFilter, "/*", null);

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.register(WebConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletHolder dispatcherServletHolder = new ServletHolder(dispatcherServlet);
        contextHandler.addServlet(dispatcherServletHolder, "/*");

        contextHandler.addServlet(new ServletHolder(miniServlet), "/custom-mvc/*");

        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();

        System.out.println("API RODANDO EM http://localhost:8080/");
        server.join();
    }
}
