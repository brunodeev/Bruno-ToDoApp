//package com.bruno.controller;
//
//import com.bruno.dao.TaskDao;
//import com.bruno.dao.TaskDaoHibernate;
//import com.bruno.model.Task;
//import com.bruno.model.TaskHibernate;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class SpringMvcTest {
//
//    @Mock
//    private SessionFactory sessionFactory;
//
//    @InjectMocks
//    private TaskDaoHibernate taskDaoHibernate;
//
//    @Test
//    void shouldCreateTask() {
//        TaskHibernate taskMockada = new TaskHibernate(null, "Tarefa mockada", false);
//
//        when(taskDaoHibernate.getTaskById(123)).thenReturn(taskMockada);
//
//        TaskHibernate taskFinded = (TaskHibernate) taskDaoHibernate.getTaskById(123);
//
//        Assertions.assertEquals(taskMockada, taskFinded);
//    }
//}
