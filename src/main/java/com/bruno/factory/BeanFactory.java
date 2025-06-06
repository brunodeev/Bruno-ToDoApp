package com.bruno.factory;

import com.bruno.dao.TaskDao;
import com.bruno.dao.TaskDaoHibernate;
import com.bruno.dao.TaskDaoJdbc;
import com.bruno.database.HibernateUtil;
import org.hibernate.SessionFactory;

public class BeanFactory {
    public static TaskDao createTaskDao(String type) {
        if (type == null) {
            throw new IllegalArgumentException("O tipo de DAO não pode ser nulo");
        }

        if (type.equals("JDBC")) {
            return new TaskDaoJdbc();
        } else if (type.equals("HIBERNATE")) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            return new TaskDaoHibernate(sessionFactory);
        } else {
            throw new IllegalArgumentException("Tipo de DAO não permitido.");
        }
    }
}
