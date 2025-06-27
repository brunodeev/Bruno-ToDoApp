package com.bruno.spring;

import com.bruno.dao.TaskDao;
import com.bruno.model.Task;
import com.bruno.model.TaskHibernate;
import com.bruno.view.ListTasksPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListTasksPageTest {

    private TaskDao taskDaoMock;
    private ListTasksPage listTasksPage;

    @BeforeEach
    public void setUp() {
        taskDaoMock = mock(TaskDao.class);
        listTasksPage = new ListTasksPage(taskDaoMock);
    }

    @Test
    public void renderNoTasksMessage() {
        when(taskDaoMock.listAllTasks()).thenReturn(Collections.emptyList());

        String html = listTasksPage.render(new HashMap<>());

        Assertions.assertNotNull(html);

        Assertions.assertTrue(html.contains("Você não tem nenhuma tarefa... :("));
    }

    @Test
    public void renderWithSomeTasks() {
        List<Task> tasks = List.of(
                new TaskHibernate(null, "Comprar miojo", false),
                new TaskHibernate(null, "Ir ao médico", false)
        );

        when(taskDaoMock.listAllTasks()).thenReturn(tasks);

        String html = listTasksPage.render(new HashMap<>());

        Assertions.assertNotNull(html);

        Assertions.assertTrue(html.contains("Comprar miojo"));
        Assertions.assertTrue(html.contains("Ir ao médico"));
    }
}
