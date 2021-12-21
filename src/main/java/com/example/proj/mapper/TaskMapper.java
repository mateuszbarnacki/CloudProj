package com.example.proj.mapper;

import com.example.proj.dto.TaskDTO;
import com.example.proj.model.Task;
import org.springframework.stereotype.Component;

@Component
public final class TaskMapper {
    private TaskMapper() {
    }

    public Task map(TaskDTO taskDTO) {
        return new Task(taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getProject(),
                taskDTO.getStatus());
    }

    public TaskDTO map(Task entity) {
        return new TaskDTO.Builder()
                .title(entity.getTitle())
                .projectName(entity.getProject())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }
}
