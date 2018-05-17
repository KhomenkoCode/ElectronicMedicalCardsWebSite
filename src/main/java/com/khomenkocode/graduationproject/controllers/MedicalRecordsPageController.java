package com.khomenkocode.graduationproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.khomenkocode.graduationproject.dao.TMedicalRecordsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
@SessionAttributes("patient")
public class MedicalRecordsPageController {

	@Autowired
	TPatientsDAO patientsDAO;
	
	@Autowired
	TMedicalRecordsDAO MedicalRecordsDAO;
	
    @GetMapping("/records")
    public String index(Model model, @ModelAttribute("patient") TPatients patient) {
    	List<TMedicalRecords> medicalRecords = MedicalRecordsDAO.findAllByPatient(patient);
    	model.addAttribute("records", medicalRecords);
        model.addAttribute("medicalrecords", true);
        return "medicalrecords";
    }
}