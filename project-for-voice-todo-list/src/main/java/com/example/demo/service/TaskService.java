package com.example.demo.service;

import com.example.demo.ExceptionHandling.HandleException;
import com.example.demo.analyzers.MyAnalyzer;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MyAnalyzer myAnalyzer;

    public Task createTask(String operation, String task, String urgency,String datetime, Long userId){
        Task newTask = new Task();
        String processedMessage = myAnalyzer.stem(task);
        newTask.setOperation(operation);
        newTask.setTask(processedMessage);
        newTask.setUrgency(urgency);
        newTask.setDatetime(datetime);
        newTask.setUserId(userId);
        return taskRepository.save(newTask);
    }

    public Task updateTask(Long id, String operation, String task, String urgency, String datetime){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()){
            String processedMessage = myAnalyzer.stem(task);
            Task existingTask = taskOptional.get();
            existingTask.setOperation(operation);
            existingTask.setTask(processedMessage);
            existingTask.setUrgency(urgency);
            existingTask.setDatetime(datetime);
            return taskRepository.save(existingTask);
        }else {
            return null;
        }

    }

    public Optional<Task> getTask(Long id){
        return taskRepository.findById(id);
    }

    public boolean deleteTask(Long id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Task> getTaskByUserId(Long userId){
        if(taskRepository.existsByUserId(userId)){
            List<Task> tasks = taskRepository.findByUSerId(userId);
        };
        return List.of();
    }

    public List<Task> getTasksByDateRange(Long userId, Integer days){
        List<Task> tasks;
        tasks = taskRepository.findByUSerId(userId);
        if(days==null || days<=0){
            return tasks;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(days);

        return tasks.stream().filter(task->{
            try{
                LocalDateTime taskData = parseDateTime(task.getDatetime());
                return taskData!= null && !taskData.isBefore(now) &&
                        !taskData.isAfter(endDate);
            }catch (Exception exception){
                return false;
            }
        }).collect(Collectors.toList());
    }

    private LocalDateTime parseDateTime(String dateTime){
        if(dateTime == null || dateTime.isEmpty()){
            return null;
        }
        try{
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }catch (Exception exception){
            try {
                return LocalDateTime.parse(dateTime,DateTimeFormatter.ISO_DATE_TIME);
            }catch (Exception exception1){
                HandleException.throwIllegarArgumentException("Invalid dateTme format:" + dateTime, exception1);
            }
        }
        return null;
    }
}
