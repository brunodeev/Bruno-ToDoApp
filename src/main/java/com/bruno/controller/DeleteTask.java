package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteTask {
    TaskDao taskDao = new TaskDaoImpl();

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("idDelete");
            taskDao.removeTaskById(Integer.parseInt(id));

            response.setStatus(200);
            response.sendRedirect("/list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
