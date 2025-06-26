package com.bruno.wicket;

import com.bruno.application.ApplicationTest;
import com.bruno.dao.TaskDao;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class ListTasksPageTest {

    private WicketTester tester;
    private TaskDao taskDaoMock;

    @BeforeEach
    public void setUp() {
        taskDaoMock = mock(TaskDao.class);
        tester = new WicketTester(new ApplicationTest(taskDaoMock));
    }

    @Test
    public void pageWithNoTasksTest() {
        when(taskDaoMock.listAllTasks()).thenReturn(Collections.emptyList());

        tester.startPage(ListTasksPage.class);

        tester.assertVisible("noTasksLabel");
        tester.assertInvisible("taskContainer");
    }
}