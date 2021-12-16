package com.example.proj.service;

import com.example.proj.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    public List<TaskDTO> getEmployeeTasks(String employeeEmail) {
        return null;
    }

    public List<TaskDTO> getTaskCreatedByEmployee(String employeeEmail) {
        return null;
    }

    public List<TaskDTO> getNotActiveTasksFromProject(String projectName) {
        return null;
    }

    public List<TaskDTO> getNotActiveTasks() {
        return null;
    }

    public TaskDTO create(TaskDTO taskDTO) {
        return null;
    }

    public TaskDTO update(TaskDTO taskDTO) {
        return null;
    }

    public void deleteAllTasksFromProject(String projectName) {

    }
}
