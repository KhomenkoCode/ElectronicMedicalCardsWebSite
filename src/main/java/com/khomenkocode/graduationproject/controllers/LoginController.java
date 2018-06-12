package com.khomenkocode.graduationproject.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.khomenkocode.graduationproject.dao.TDoctorsDAO;
import com.khomenkocode.graduationproject.dao.TMedicalRecordsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;


@Controller
@SessionAttributes({"patient","admin","doctor"})
public class LoginController {

	@Autowired
	TPatientsDAO patientsDAO;

	@Autowired
	TDoctorsDAO doctorsDAO;

	@Autowired
	TMedicalRecordsDAO medicalRecordsDAO;
	
    @GetMapping("/patientlogin")
    public String patientLogin(Model model) {
    	model.addAttribute("login", true);
        return "patientlogin";
    }
    
    @PostMapping("/patientlogin")
    public String patientLoginPost(Model model,@RequestParam("email") String email, @RequestParam("password") String password) {
    	try{
    		TPatients patient = patientsDAO.findByEmailAndPassword(email, password);
    		model.addAttribute("patient", patient);
    		return "redirect:/records";
    	}	catch(NoResultException e){        
    		return "patientlogin";
        }
        
    }
    
    
    @GetMapping("/doctorlogin")
    public String doctorLogin(Model model) {
        model.addAttribute("login", true);
        return "doctorlogin";
    }
   
    @PostMapping("/doctorlogin")
    public String doctorLoginPost(Model model,@RequestParam("license") String license, @RequestParam("password") String password) {
    	try{
    		TDoctors doctor = doctorsDAO.findByLicenseNumberAndPassword(license, password);
    		model.addAttribute("doctor", doctor);
    		return "redirect:/records";
    	}	catch(NoResultException e){        
    		return "doctorlogin";
        }
        
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
    
    @GetMapping("/adminlogin")
    public String adminLogin(Model model) {
        model.addAttribute("login", true);
        return "adminlogin";
    }
    
    private static Map<String, String> adminlogins = new HashMap<>();
    
    static{
    	adminlogins.put("admin", "1234");
    	adminlogins.put("admin2", "1234");
    	adminlogins.put("admin3", "1234");
    }
    
    
    @PostMapping("/adminlogin")
    public String adminLoginPost(Model model,@RequestParam("login") String login, @RequestParam("password") String password) {
    	
    	if(adminlogins.get(login).equals(password)){

            model.addAttribute("admin", true);
    		return "redirect:/";
    	}else{	
    		
    		return "adminlogin";
    	}
        
    }

    @GetMapping("/logout")
    public String logOut(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }
    
}