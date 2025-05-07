package com.example.demo.Util;

import com.example.demo.analyzers.MyAnalyzer;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserHelper {

    @Autowired
    MyAnalyzer myAnalyzer;

    public boolean isValidUser(String email, String password, User user){
        String processedEmail = myAnalyzer.stem(email);
        if (processedEmail.equals(email) && user.getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
