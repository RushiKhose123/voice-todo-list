package com.example.demo.service;

import com.example.demo.ExceptionHandling.HandleException;
import com.example.demo.entity.Notify;
import com.example.demo.service.serviceInterface.INotifyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public class EmailService implements INotifyUser {
    @Autowired
    private JavaMaleSender mailSender;

    @Value("rushikhose2001@gmail.com")
    private String fromEmail;

    public boolean sendTaskNotification(String to, String subject, String message){
       try{
           SimpleMailMessage email = new SimpleMailMessage();
           email.setFrom(fromEmail);
           email.setTo(to);
           email.setSubject(subject);
           email.setText(message);
           mailSender.send(email);
           return true;
       }catch (MailException e){
           HandleException.throwMailException("Unable to send mail", e);
           return false;
       }
    }

    @Override
    public boolean notifyuser(Notify notify) {
        return sendTaskNotification(notify.getEmail(), notify.getMetaData(), notify.getMessage());
    }
}
