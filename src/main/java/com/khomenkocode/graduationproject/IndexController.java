package com.khomenkocode.graduationproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
public class IndexController {

	@Autowired
	TPatientsDAO dao;
	
    @GetMapping("/")
    public String index(Model model) {
    	TPatients a = dao.findById("abcd");
        model.addAttribute("name", a.getPatientName());

        return "index";
    }
}