package com.bruno.application;

import com.bruno.dao.TaskDao;
import com.bruno.wicket.ListTasksPage;
import org.apache.wicket.Page;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.StaticApplicationContext;

public class ApplicationTest extends WebApplication {

    private final TaskDao taskDaoMock;

    public ApplicationTest(TaskDao taskDaoMock) {
        this.taskDaoMock = taskDaoMock;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return ListTasksPage.class;
    }

    @Override
    public void init() {
        super.init();

        StaticApplicationContext context = new StaticApplicationContext();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        beanFactory.registerSingleton("taskDao", taskDaoMock);

        getComponentInstantiationListeners().add(new SpringComponentInjector(this, context, true));
    }
}