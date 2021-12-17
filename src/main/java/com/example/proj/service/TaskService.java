package com.example.proj.service;

import com.example.proj.dto.TaskDTO;
import com.example.proj.mapper.TaskMapper;
import com.example.proj.model.TaskEntity;
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
            entity.setProjectName(taskDTO.getProjectName());
            entity.setTitle(taskDTO.getTitle());
            entity.setDescription(taskDTO.getDescription());
            entity.setStatus(taskDTO.getStatus());

            return Optional.of(taskRepository.save(entity))
                    .map(taskMapper::map);
        }
        return Optional.empty();
    }

    public void deleteAllTasksFromProject(String projectName) {
        taskRepository.customQueryDeleteAllTasksFromProject(projectName);
    }
}
