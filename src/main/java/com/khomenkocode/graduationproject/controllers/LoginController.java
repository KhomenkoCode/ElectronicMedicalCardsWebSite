package com.khomenkocode.graduationproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.khomenkocode.graduationproject.dao.TMedicalRecordsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;


@Controller
@SessionAttributes("patient")
public class LoginController {

	@Autowired
	TPatientsDAO patientsDAO;

	@Autowired
	TMedicalRecordsDAO MedicalRecordsDAO;
	
    @GetMapping("/patientlogin")
    public String index(Model model) {
    	//TPatients a = dao.findById("abcd");
        //model.addAttribute("name", a.getPatientName());
        model.addAttribute("login", true);
        return "login";
    }
    
    @GetMapping("/qrcheck")
    public String qrCheck(Model model,  @RequestParam(value = "qr", required = false) String qrcode){
    	if(qrcode == null)
    		return "redirect:/patientlogin";
    	else {
    		TPatients patient = patientsDAO.findById(qrcode);
        	if(patient == null)
        		return "redirect:/patientlogin";
        	model.addAttribute("patient", patient);
        	return "redirect:/records";
    	}
    }

    @GetMapping("/logout")
    public String logOut(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }
    
}