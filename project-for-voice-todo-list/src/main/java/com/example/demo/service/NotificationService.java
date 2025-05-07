package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    EmailService emailService;

    public void sendTaskNotification(Task task, User user, boolean isUpdated){
        if (user!=null){
            String action = isUpdated ? "updated": "created";
            String emaiMessage = String.format("Task %s : %s\n Urgency %s\n Due: %s", action, task.getTask(),
                     task.getUrgency(), task.getDatetime());
            String subject = "Task "+action.substring(0,1).toUpperCase()+action.substring(1);
            emailService.sendTaskNotification(user.getEmail(), subject, emaiMessage);
        }
    }
}
