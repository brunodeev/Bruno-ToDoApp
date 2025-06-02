package com.bruno.servlet;

import com.bruno.dao.TaskDao;
import com.bruno.daoImpl.TaskDaoImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteTask extends HttpServlet {
    TaskDao taskDao = new TaskDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
