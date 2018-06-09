package com.khomenkocode.graduationproject.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
@SessionAttributes({"patient","admin","doctor"})
public class ProfileContorller {
	@GetMapping("/profile")
	public String patientRegistrationGet(Model model, HttpSession httpSession) {

		TPatients pat = (TPatients) httpSession.getAttribute("patient");
		TPatients adm = (TPatients) httpSession.getAttribute("admin");
		TPatients doc = (TPatients) httpSession.getAttribute("doctor");

		if(pat != null){
			
			System.out.println("pat");
		}
		if(adm != null){
			System.out.println("adm");
		}
		if(doc != null){
			System.out.println("doc");
			model.addAttribute("doctor", doc);
		}
		
		return "profile";
    }
	
}
