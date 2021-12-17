package com.example.proj.controller;

import com.example.proj.dto.TaskDTO;
import com.example.proj.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{projectName}")
    public ResponseEntity<List<TaskDTO>> getNotActiveTasksFromProject(@PathVariable String projectName) {
        return ResponseEntity.ok(taskService.getNotActiveTasksFromProject(projectName));
    }

    @PostMapping("/{projectName}")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody @Valid TaskDTO task) {
        return taskService.update(task)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't update task with id: " + task.getId()));
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<?> deleteAllTasksFromProject(@PathVariable String projectName) {
        taskService.deleteAllTasksFromProject(projectName);
        return ResponseEntity.ok().build();
    }
}
