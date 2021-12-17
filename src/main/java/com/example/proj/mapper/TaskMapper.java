package com.example.proj.mapper;

import com.example.proj.dto.TaskDTO;
import com.example.proj.model.TaskEntity;

public final class TaskMapper {
    private TaskMapper() {
    }

    public static TaskEntity map(TaskDTO taskDTO) {
        return new TaskEntity(taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getProjectName(),
                taskDTO.getStatus());
    }

    public static TaskDTO map(TaskEntity entity) {
        return new TaskDTO.Builder()
                .title(entity.getTitle())
                .projectName(entity.getProjectName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }
}
