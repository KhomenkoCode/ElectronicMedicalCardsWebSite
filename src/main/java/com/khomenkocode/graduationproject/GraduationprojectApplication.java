package com.khomenkocode.graduationproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class GraduationprojectApplication {
	public static ClassPathXmlApplicationContext context;
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("app-context.xml");	
		SpringApplication.run(GraduationprojectApplication.class, args);
	}
}
