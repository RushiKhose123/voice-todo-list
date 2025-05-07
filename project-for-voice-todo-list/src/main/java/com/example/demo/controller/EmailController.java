package com.example.demo.controller;

import com.example.demo.entity.EmailRequest;
import com.example.demo.entity.Notify;
import com.example.demo.service.EmailService;
import com.example.demo.service.serviceInterface.INotifyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:5500")
public class EmailController {


    private final INotifyUser emailService;

    public EmailController(){
        emailService = new EmailService();
    }

    @PostMapping("/send")
    public ResponseEntity<?>  sendEmail(@RequestBody EmailRequest emailRequest){
        try{
            Notify notify = Notify.builder().email(emailRequest.getTo()).message(emailRequest.getMessage()).
                    metaData(emailRequest.getSubject()).build();
            emailService.notifyuser(
                    notify);
            return ResponseEntity.ok().body("Email send successfully");
        }catch(Exception exception){
            return ResponseEntity.badRequest().body("Failed to send email " + exception.getMessage());
        }
    }
}
