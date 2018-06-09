package com.khomenkocode.graduationproject.controllers;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
@SessionAttributes({"patient","admin","doctor"})
public class StaticPagesController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("index", true);
        return "index";
    }
    
    @GetMapping("/test")
    public String test(Model model) {
    	
    	int num = 0;

    	try {
    		URL obj = new URL("http://localhost:8080");
    		Date start = null;
    		HttpURLConnection connection;
    		
    		do{
    			connection = (HttpURLConnection) obj.openConnection();
    			connection.setRequestMethod("GET");
    			connection.getInputStream();
    			connection.disconnect();
    			if(start == null)
    				start = new Date();
    			num++;
			} while(((new Date()).getTime()) - start.getTime() < 1000);
    		
    		start = new Date();
    		connection = (HttpURLConnection) obj.openConnection();
    		connection.setRequestMethod("GET");
			connection.getInputStream();
			connection.disconnect();
			model.addAttribute("time", ((new Date()).getTime() - start.getTime()));
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	model.addAttribute("num", num);
    	return "test";
    }
        
}