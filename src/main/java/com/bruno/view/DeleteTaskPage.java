package com.bruno.view;

import com.bruno.annotation.Route;
import com.bruno.dao.TaskDao;
import com.bruno.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Route("/delete")
public class DeleteTaskPage implements Page {

    @Autowired
    private TaskDao taskDao;

    @Override
    public String render(Map<String, Object> parameters) {

        if (parameters.containsKey("idDelete")) {
            try {
                String id = (String) parameters.get("idDelete");
                taskDao.removeTaskById(Integer.parseInt(id));

                return "<meta http-equiv='refresh' content='0; URL=/custom-mvc/list'>";
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        return "";
    }
}
