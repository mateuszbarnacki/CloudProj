package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.mapper.TaskMapper;
import com.example.proj.model.Task;
import com.example.proj.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Optional<TaskDTO> createTask(EmployeeDTO employeeDTO, TaskDTO taskDTO) {
        return taskRepository.customQueryCreateTask(employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getEmail(),
                        taskDTO.getTitle(), taskDTO.getProject(), taskDTO.getDescription(), taskDTO.getStatus().name())
                .map(taskMapper::map);
    }

    public Optional<TaskDTO> startTask(TaskDTO taskDTO, EmployeeDTO employeeDTO) {
        return taskRepository.customQueryStartTask(taskDTO.getTitle(), taskDTO.getProject(), taskDTO.getDescription(),
                        taskDTO.getStatus().name(), employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getEmail())
                .map(taskMapper::map);
    }

    public Optional<TaskDTO> finishTask(TaskDTO taskDTO, EmployeeDTO employeeDTO) {
        return taskRepository.customQueryFinishTask(taskDTO.getTitle(), taskDTO.getProject(), taskDTO.getDescription(),
                        taskDTO.getStatus().name(), employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getEmail())
                .map(taskMapper::map);
    }

    public List<TaskDTO> getAllAvailableTasks() {
        return taskRepository.customQueryGetAllNotActiveTasks()
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getCurrentTasks(EmployeeDTO employeeDTO) {
        return taskRepository.customQueryGetCurrentTasks(employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getEmail())
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getSuggestedTasks(EmployeeDTO employeeDTO) {
        return taskRepository.customQueryGetSuggestedTasks(employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getEmail())
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getNotActiveTasksFromProject(String projectName) {
        return taskRepository.customQueryGetNotActiveTasksFromProject(projectName)
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> update(TaskDTO taskDTO) {
        if (Objects.nonNull(taskDTO.getId())) {
            Task entity = taskRepository.findById(taskDTO.getId())
                    .orElseThrow(() ->
                            new NoSuchElementException("Couldn't find task with id: " + taskDTO.getId()));
            entity.setProject(taskDTO.getProject());
            entity.setTitle(taskDTO.getTitle());
            entity.setDescription(taskDTO.getDescription());
            entity.setStatus(taskDTO.getStatus());

            return Optional.of(taskRepository.save(entity))
                    .map(taskMapper::map);
        }
        return Optional.empty();
    }

    public List<TaskDTO> generateReport(EmployeeDTO employeeDTO) {
        return taskRepository.customQueryGenerateReport(employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getEmail())
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public void deleteAllTasksFromProject(String projectName) {
        taskRepository.customQueryDeleteAllTasksFromProject(projectName);
    }
}
