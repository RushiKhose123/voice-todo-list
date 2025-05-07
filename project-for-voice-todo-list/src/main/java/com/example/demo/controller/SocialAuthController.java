package com.example.demo.controller;

import com.example.demo.entity.SocialLoginRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/social-auth")
public class SocialAuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> socialLogin(@RequestBody SocialLoginRequest request){
        try {
            String email = request.getUserData().getEmail();

            Optional<User> existinUser = userRepository.findByEmail(email);
            if (existinUser.isPresent()){
                User user = existinUser.get();
                Map<String,Object> response = new HashMap<>();
                response.put("user", Map.of(
                        "id", user.getId(),
                        "email", user.getEmail()
                ));
            }else {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword("social_"+System.currentTimeMillis());
                User savedUser = userRepository.save(newUser);
                Map<String,Object> response = new HashMap<>();
                response.put("user", Map.of(
                        "id", savedUser.getId(),
                        "email", savedUser.getEmail()
                ));
                return ResponseEntity.ok(response);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","failed to pricess social login"));
        }
    }
}
