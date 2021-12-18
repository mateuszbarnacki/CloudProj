package com.example.proj.service;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.mapper.DeveloperMapper;
import com.example.proj.mapper.ProductOwnerMapper;
import com.example.proj.mapper.TaskMapper;
import com.example.proj.mapper.TechLeaderMapper;
import com.example.proj.model.DeveloperEntity;
import com.example.proj.model.ProductOwnerEntity;
import com.example.proj.model.TaskEntity;
import com.example.proj.model.TechLeaderEntity;
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
    private final DeveloperMapper developerMapper;
    private final TechLeaderMapper techLeaderMapper;
    private final ProductOwnerMapper productOwnerMapper;

    public Optional<TaskDTO> createTask(EmployeeDTO employeeDTO, TaskDTO taskDTO) {
        TechLeaderEntity techLeaderEntity = techLeaderMapper.map(employeeDTO);
        TaskEntity taskEntity = taskMapper.map(taskDTO);

        return taskRepository.customQueryCreateTask(techLeaderEntity, taskEntity)
                .map(taskMapper::map);
    }

    public Optional<TaskDTO> startTask(TaskDTO taskDTO, EmployeeDTO employeeDTO) {
        TaskEntity task = taskMapper.map(taskDTO);
        DeveloperEntity developer = developerMapper.map(employeeDTO);

        return taskRepository.customQueryStartTask(task, developer)
                .map(taskMapper::map);
    }

    public Optional<TaskDTO> finishTask(TaskDTO taskDTO, EmployeeDTO employeeDTO) {
        TaskEntity task = taskMapper.map(taskDTO);
        DeveloperEntity developer = developerMapper.map(employeeDTO);

        return taskRepository.customQueryFinishTask(task, developer)
                .map(taskMapper::map);
    }

    public List<TaskDTO> getAllAvailableTasks() {
        return taskRepository.customQueryGetAllNotActiveTasks()
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getCurrentTasks(EmployeeDTO employeeDTO) {
        DeveloperEntity entity = developerMapper.map(employeeDTO);

        return taskRepository.customQueryGetCurrentTasks(entity)
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getSuggestedTasks(EmployeeDTO employeeDTO) {
        DeveloperEntity entity = developerMapper.map(employeeDTO);

        return taskRepository.customQueryGetSuggestedTasks(entity)
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
            TaskEntity entity = taskRepository.findById(taskDTO.getId())
                    .orElseThrow(() ->
                            new NoSuchElementException("Couldn't find task with id: " + taskDTO.getId()));
            entity.setProject(taskDTO.getProjectName());
            entity.setTitle(taskDTO.getTitle());
            entity.setDescription(taskDTO.getDescription());
            entity.setStatus(taskDTO.getStatus());

            return Optional.of(taskRepository.save(entity))
                    .map(taskMapper::map);
        }
        return Optional.empty();
    }

    public List<TaskDTO> generateReport(EmployeeDTO employeeDTO) {
        ProductOwnerEntity entity = productOwnerMapper.map(employeeDTO);

        return taskRepository.customQueryGenerateReport(entity)
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    public void deleteAllTasksFromProject(String projectName) {
        taskRepository.customQueryDeleteAllTasksFromProject(projectName);
    }
}
