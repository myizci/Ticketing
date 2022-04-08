package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

   private final TaskService taskService;
    private final ProjectService projectService;
    private final  UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createTask(Model model){
     model.addAttribute("task",new TaskDTO());
     model.addAttribute("tasks",taskService.findAll());
     model.addAttribute("projects",projectService.findAll());
     model.addAttribute("employees",userService.findEmployees());

        return "task/create";
    }

    @PostMapping("/create")
    public  String insertTask(TaskDTO task){

        taskService.save(task);

        return "redirect:/task/create";
    }

    @GetMapping("/status-update")
    public String updateStatus(Model model){


        return "task/status-update";
    }




    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id){

        taskService.deleteById(id);

        return "redirect:/task/create";

    }

    @GetMapping("/update/{taskId}")
    public String editProject(@PathVariable("taskId") Long taskId, Model model){
        model.addAttribute("task", taskService.findById(taskId));
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees",userService.findEmployees());
        model.addAttribute("tasks",taskService.findAll());

        return "/task/update";
    }

    @PostMapping("/update/{taskId}")

    public String updateTask(@PathVariable("taskId") Long taskId, TaskDTO task){
        task.setId(taskId);
        taskService.update(task);
        return "redirect:/task/create";
    }

}
