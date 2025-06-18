package com.bruno.wicket;

import com.bruno.dao.TaskDao;
import com.bruno.model.Task;
import com.bruno.model.TaskHibernate;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class ListTasksPage extends WebPage {

    @SpringBean
    private TaskDao taskDao;

    public ListTasksPage() {
        List<Task> tasks = taskDao.listAllTasks();

        ListView<Task> listView = new ListView<>("tasks", tasks) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                Task task = item.getModelObject();

                item.add(new Label("title", Model.of(task.getName())));
                item.add(new Label("completed", Model.of(task.isCompleted() ? "Sim" : "Não")));
            }
        };

        add(listView);
    }
}
