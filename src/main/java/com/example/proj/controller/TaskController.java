package com.example.proj.controller;

import com.example.proj.dto.EmployeeDTO;
import com.example.proj.dto.EmployeeWithTaskDTO;
import com.example.proj.dto.TaskDTO;
import com.example.proj.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid EmployeeWithTaskDTO dto) {
        return taskService.createTask(dto.getEmployee(), dto.getTask())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't create task!"));
    }

    @PatchMapping("/start")
    public ResponseEntity<TaskDTO> startTask(@RequestBody @Valid EmployeeWithTaskDTO dto) {
        return taskService.startTask(dto.getTask(), dto.getEmployee())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UnsupportedOperationException("Couldn't start this task."));
    }

    @PatchMapping("/finish")
    public ResponseEntity<TaskDTO> finishTask(@RequestBody @Valid EmployeeWithTaskDTO dto) {
        return taskService.finishTask(dto.getTask(), dto.getEmployee())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UnsupportedOperationException("Couldn't finish this task."));
    }

    @GetMapping("/available")
    public ResponseEntity<List<TaskDTO>> getAllAvailableTasks() {
        return ResponseEntity.ok(taskService.getAllAvailableTasks());
    }

    @GetMapping("/current_task")
    public ResponseEntity<List<TaskDTO>> getCurrentTasks(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(taskService.getCurrentTasks(employeeDTO));
    }

    @GetMapping("/suggested_task")
    public ResponseEntity<List<TaskDTO>> getSuggestedTasks(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(taskService.getSuggestedTasks(employeeDTO));
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<List<TaskDTO>> getNotActiveTasksFromProject(@PathVariable String projectName) {
        return ResponseEntity.ok(taskService.getNotActiveTasksFromProject(projectName));
    }

    @PostMapping("")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody @Valid TaskDTO task) {
        return taskService.update(task)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("Couldn't update task with id: " + task.getId()));
    }

    @GetMapping("/report")
    public ResponseEntity<List<TaskDTO>> generateReport(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(taskService.generateReport(employeeDTO));
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<?> deleteAllTasksFromProject(@PathVariable String projectName) {
        taskService.deleteAllTasksFromProject(projectName);
        return ResponseEntity.ok().build();
    }
}
