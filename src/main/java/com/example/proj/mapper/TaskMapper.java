package com.example.proj.mapper;

import com.example.proj.dto.TaskDTO;
import com.example.proj.model.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public final class TaskMapper {
    private TaskMapper() {
    }

    public TaskEntity map(TaskDTO taskDTO) {
        return new TaskEntity(taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getProjectName(),
                taskDTO.getStatus());
    }

    public TaskDTO map(TaskEntity entity) {
        return new TaskDTO.Builder()
                .title(entity.getTitle())
                .projectName(entity.getProject())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }
}
