package com.bruno.view;

import com.bruno.annotation.Route;
import com.bruno.dao.TaskDao;
import com.bruno.dao.TaskDaoJdbc;
import com.bruno.model.Page;
import java.util.Map;

@Route("/delete")
public class DeleteTaskPage implements Page {

    private final TaskDao taskDao = new TaskDaoJdbc();

    @Override
    public String render(Map<String, Object> parameters) {

        if (parameters.containsKey("idDelete")) {
            try {
                String id = (String) parameters.get("idDelete");
                taskDao.removeTaskById(Integer.parseInt(id));

                return "<meta http-equiv='refresh' content='0; URL=/list'>";
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        return "";
    }
}
