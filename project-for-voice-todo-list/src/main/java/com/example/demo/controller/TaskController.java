package com.example.demo.controller;

import com.example.demo.converters.EntityToResponse;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.TaskResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.NotificationService;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){

        Task createdTask = taskService.createTask(task.getOperation(), task.getTask(), task.getUrgency(), task.getDatetime(), task.getUserId());
        if(createdTask != null && task.getUserId() != null){
            Optional<User> user = userRepository.findById(task.getUserId());
            user.ifPresent(userData -> notificationService.sendTaskNotification(task, userData, false));
        }
        return ResponseEntity.ok(createdTask);

    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponse>> getTaskByDateRange(@RequestParam(required = true) Long userId, @RequestParam(required = false) Integer days){
        List<Task> tasks = taskService.getTasksByDateRange(userId,days);
        return ResponseEntity.ok(EntityToResponse.convertTasksToTaskResponses(tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        Optional<Task> task = taskService.getTask(id);
        return ResponseEntity.ok(task.get());
    }

    @PutMapping("/update/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Task updatedTask = taskService.updateTask(id,task.getOperation(), task.getTask(), task.getUrgency(), task.getDatetime());

        if(updatedTask != null){
            return ResponseEntity.ok(updatedTask);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/task/{id}")
    public ResponseEntity<Void> getTasks(@PathVariable Long id){
        boolean deleted = taskService.deleteTask(id);
        if(deleted){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
