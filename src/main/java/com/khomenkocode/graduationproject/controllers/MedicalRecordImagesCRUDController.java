package com.khomenkocode.graduationproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.khomenkocode.graduationproject.dao.TDoctorsDAO;
import com.khomenkocode.graduationproject.dao.TMedicalRecordsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;

@Controller
@SessionAttributes({"patient","admin","doctor"})
public class MedicalRecordImagesCRUDController {
	
	@Autowired
	TPatientsDAO patientsDAO;

	@Autowired
	TDoctorsDAO doctorsDAO;
	
	@Autowired
	TMedicalRecordsDAO medicalRecordsDAO;
	
	@GetMapping("/addimage")
    public String patientAddRecord(Model model, @RequestParam(value = "record") String record) {
    	model.addAttribute("records", true);
    	model.addAttribute("errormessage", "");
        return "addimage";
    }
}
