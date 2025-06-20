package com.bruno.wicket;

import com.bruno.dao.TaskDao;
import com.bruno.model.TaskHibernate;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class CreateTaskPage extends WebPage {

    @SpringBean
    private TaskDao taskDao;

    public CreateTaskPage() {

        TaskHibernate task = new TaskHibernate();

        Model<String> completedModel = Model.of("Não concluído");

        List<String> statusOptions = List.of("Não concluído", "Concluído");

        DropDownChoice<String> completedChoice = new DropDownChoice<>(
                "completed", completedModel, statusOptions
        );

        Form<Void> form = new Form<>("taskForm") {

            @Override
            protected void onSubmit() {
                super.onSubmit();

                task.setCompleted("Concluído".equals(completedModel.getObject()));

                taskDao.addTask(task);
                setResponsePage(ListTasksPage.class);
            }
        };

        form.add(new TextField<>("name", new PropertyModel<>(task, "name")));
        form.add(completedChoice);

        add(form);
    }
}
