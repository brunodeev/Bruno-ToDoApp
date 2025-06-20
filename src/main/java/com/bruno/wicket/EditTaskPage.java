package com.bruno.wicket;

import com.bruno.dao.TaskDao;
import com.bruno.model.Task;
import com.bruno.model.TaskHibernate;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class EditTaskPage extends WebPage {

    @SpringBean
    private TaskDao taskDao;

    public EditTaskPage(TaskHibernate task) {

        Model<String> completedModel = Model.of(task.isCompleted() ? "Concluído" : "Não concluído");

        Form<Void> form = new Form<>("taskForm") {

            @Override
            protected void onSubmit() {

                task.setCompleted("Concluído".equals(completedModel.getObject()));
                taskDao.updateTask(task);
                setResponsePage(ListTasksPage.class);
            }
        };

        form.add(new TextField<>("name", new PropertyModel<>(task, "name")));
        form.add(new DropDownChoice<>(
                "completed", completedModel, List.of("Não concluído", "Concluído")
        ));

        add(form);
    }
}
