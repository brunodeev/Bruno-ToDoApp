package com.bruno.wicket;

import com.bruno.application.ApplicationTest;
import com.bruno.dao.TaskDao;
import com.bruno.dao.TaskDaoHibernate;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateTaskPageTest {

    private WicketTester tester;
    private TaskDao taskDaoMock;

    @BeforeEach
    public void setUp() {
        taskDaoMock = mock(TaskDao.class);
        tester = new WicketTester(new ApplicationTest(taskDaoMock));
    }

    @Test
    public void startPage() {
        tester.startPage(CreateTaskPage.class);
    }
}
