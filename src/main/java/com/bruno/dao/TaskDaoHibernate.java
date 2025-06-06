package com.bruno.dao;

import com.bruno.model.Task;
import com.bruno.model.TaskHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TaskDaoHibernate implements TaskDao {

    private final SessionFactory sessionFactory;

    public TaskDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer addTask(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(task);

        transaction.commit();
        session.close();

        return task.getId();
    }

    @Override
    public void removeTaskById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TaskHibernate task = session.find(TaskHibernate.class, id);

        if(task != null) {
            session.remove(task);
            transaction.commit();
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        Session session = sessionFactory.openSession();
        TaskHibernate task = session.find(TaskHibernate.class, id);

        session.close();

        return task;
    }

    @Override
    public List<Task> listAllTasks() {
        Session session = sessionFactory.openSession();
        List<Task> tasks = session.createQuery("from TaskHibernate", Task.class).getResultList();
        session.close();
        return tasks;
    }

    @Override
    public void updateTask(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(task);

        transaction.commit();
        session.close();
    }
}
