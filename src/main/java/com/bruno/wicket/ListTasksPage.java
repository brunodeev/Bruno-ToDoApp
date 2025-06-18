package com.bruno.wicket;

import com.bruno.dao.TaskDao;
import com.bruno.model.Task;
import com.bruno.model.TaskHibernate;
import com.bruno.view.EditTaskPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
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

                item.add(new Label("id", Model.of(task.getId())));
                item.add(new Label("title", Model.of(task.getName())));
                item.add(new Label("completed", Model.of(task.isCompleted() ? "Concluído" : "Não concluído")));

                item.add(new Link<Void>("edit") {

                    @Override
                    public void onClick() {
                    }
                });

                item.add(new Link<Void>("delete") {

                    @Override
                    public void onClick() {

                        taskDao.removeTaskById(task.getId());

                        setResponsePage(ListTasksPage.class);
                    }
                });
            }
        };

        add(listView);
    }
}
