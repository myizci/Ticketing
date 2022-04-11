package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO object) {

        if(object.getProjectStatus()==null){
            object.setProjectStatus(Status.OPEN);
        }

        return super.save(object.getProjectCode(), object);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public ProjectDTO findById(String projectCode) {
        return super.findBy(projectCode);
    }

    @Override
    public void deleteById(String projectCode) {

        super.deleteById(projectCode);
    }

    @Override
    public void update(ProjectDTO object) {
ProjectDTO newProject= findById(object.getProjectCode());
        if(object.getProjectStatus()==null){
            object.setProjectStatus(newProject.getProjectStatus());
        }

        super.update(object.getProjectCode(), object);
    }

    @Override
    public void complete(ProjectDTO projectDTO) {

        projectDTO.setProjectStatus(Status.COMPLETE);
        super.save(projectDTO.getProjectCode(), projectDTO);

    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

        List<ProjectDTO> projectList =

                findAll().stream().filter(project -> project.getAssignedManager().equals(manager))
                        .map(project -> {
                            List<TaskDTO> taskList = taskService.findTasksByManager(manager);

                            int completedTaskCount = (int)taskList.stream().filter(task->task.getProject().equals(project)&&
                                    task.getTaskStatus() == Status.COMPLETE).count();

                            int unfinishedTaskCount = (int)taskList.stream().filter(task->task.getProject().equals(project) &&
                                    task.getTaskStatus()!=Status.COMPLETE).count();

                            project.setCompleteTaskCount(completedTaskCount);
                            project.setUnfinishedTaskCount(unfinishedTaskCount);

                            return project;
                        }).collect(Collectors.toList());

        return projectList;

    }
}
