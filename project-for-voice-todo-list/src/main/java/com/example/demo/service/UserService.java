package com.example.demo.service;

import com.example.demo.analyzers.MyAnalyzer;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyAnalyzer myAnalyzer;

    public User findUserByEmail(String email){
        if (userRepository.existsByEmail(email)) {
            Optional<User> user = userRepository.findByEmail(email);
            return user.orElse(null);
        }
        return null;
    }

    public User saveToDb(String email, String password){

        String processedEmail = myAnalyzer.stem(email);
        User user = User.builder().email(processedEmail).password(password).build();
        if (userRepository.existsByEmail(email)){
            return null;
        }else{
            return userRepository.save(user);
        }
    }

}
