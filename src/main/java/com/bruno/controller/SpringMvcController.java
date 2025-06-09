package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.factory.BeanFactory;
import com.bruno.model.Task;
import com.bruno.model.TaskHibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SpringMvcController {

    TaskDao taskDao = BeanFactory.createTaskDao("HIBERNATE");

    @GetMapping("/list")
    public String list(Model model) {
        List<Task> tasks = taskDao.listAllTasks();
        model.addAttribute("tasks", tasks);

        return "list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("task", new TaskHibernate());

        return "create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute TaskHibernate task) {
        taskDao.addTask(task);

        return "redirect:/list";
    }

    @PostMapping("/delete")
    public String deleteTask(@ModelAttribute("idDelete") Integer id) {
        taskDao.removeTaskById(id);

        return "redirect:/list";
    }
}
