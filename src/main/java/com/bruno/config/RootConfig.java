package com.bruno.config;

import com.bruno.dao.TaskDao;
import com.bruno.dao.TaskDaoHibernate;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Bean
    public TaskDao taskDao(SessionFactory sessionFactory) {
        return new TaskDaoHibernate(sessionFactory);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }
}
