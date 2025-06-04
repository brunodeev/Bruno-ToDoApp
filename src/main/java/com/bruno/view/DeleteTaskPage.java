package com.bruno.view;

import com.bruno.annotation.Rota;
import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import com.bruno.model.Page;
import java.util.Map;

@Rota("/delete")
public class DeleteTaskPage implements Page {

    private final TaskDao taskDao = new TaskDaoImpl();

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
