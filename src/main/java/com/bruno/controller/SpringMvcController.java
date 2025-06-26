package com.bruno.controller;

import com.bruno.dao.TaskDao;
import com.bruno.model.Task;
import com.bruno.model.TaskHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SpringMvcController {

    private final TaskDao taskDao;

    public SpringMvcController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GetMapping("/spring-mvc/list")
    public String list(Model model) {
        List<Task> tasks = taskDao.listAllTasks();
        model.addAttribute("tasks", tasks);

        return "list";
    }

    @GetMapping("/spring-mvc/create")
    public String createPage(Model model) {
        model.addAttribute("task", new TaskHibernate());

        return "create";
    }

    @PostMapping("/spring-mvc/create")
    public String createTask(@ModelAttribute TaskHibernate task) {
        taskDao.addTask(task);

        return "redirect:/spring-mvc/list";
    }

    @PostMapping("/spring-mvc/delete")
    public String deleteTask(@ModelAttribute("idDelete") Integer id) {
        taskDao.removeTaskById(id);

        return "redirect:/spring-mvc/list";
    }

    @GetMapping("/spring-mvc/edit")
    public String editPage(@ModelAttribute("idEdit") Integer id, Model model) {
        Task task = taskDao.getTaskById(id);
        model.addAttribute("task", task);

        return "edit";
    }

    @PostMapping("/spring-mvc/edit")
    public String editTask(@ModelAttribute TaskHibernate task) {
        taskDao.updateTask(task);

        return "redirect:/spring-mvc/list";
    }
}
