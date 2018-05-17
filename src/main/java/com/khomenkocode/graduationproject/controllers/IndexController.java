package com.khomenkocode.graduationproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
//@SessionAttributes("patient")
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("index", true);
        return "index";
    }
    
    @GetMapping("/test")
    public String test(Model model) {
    	//model.addAttribute("patient", new TDoctors());
        model.addAttribute("index", true);
        return "index";
    }
    
    
    
}