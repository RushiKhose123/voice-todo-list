package com.example.demo;

import com.example.demo.analyzers.MyAnalyzer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ProjectForVoiceTodoListApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjectForVoiceTodoListApplication.class, args);
		Set<String> strings = new HashSet<>();
		MyAnalyzer myAnalyzer = new MyAnalyzer(strings);
		String res = myAnalyzer.stem("nike shoes for men");
		System.out.println(res);
	}

}
