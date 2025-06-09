package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.factory.BeanFactory;
import com.bruno.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SpringMvcController {

    TaskDao taskDao = BeanFactory.createTaskDao("HIBERNATE");

    @GetMapping("/list")
    public String list(Model model) {
        List<Task> tasks = taskDao.listAllTasks();
        model.addAttribute("tasks", tasks);

        return "hello";
    }
}
