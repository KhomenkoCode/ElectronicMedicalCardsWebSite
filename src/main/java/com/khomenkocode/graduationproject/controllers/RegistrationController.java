package com.khomenkocode.graduationproject.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.khomenkocode.graduationproject.dao.TDoctorsDAO;
import com.khomenkocode.graduationproject.dao.THospitalDoctorsDAO;
import com.khomenkocode.graduationproject.dao.THospitalsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.THospitalDoctors;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
public class RegistrationController {

	@Autowired
	TDoctorsDAO doctorsDAO;
	
	@Autowired
	TPatientsDAO patientsDAO;
	
	@Autowired
	THospitalsDAO hospitalDAO;
	
	@Autowired
	THospitalDoctorsDAO hospitalDoctorsDAO;
	
	private Model setHospitals(Model model){
		model.addAttribute("hospitals", hospitalDAO.getAll() );
		return model;
	}
	
	@GetMapping("/patientregistration")
    public String patientRegistrationGet(Model model) {
    	setHospitals(model);
    	model.addAttribute("errormessage", "");
        return "patientregister";
    }
    
	@Transactional
	@PostMapping("/patientregistration")
    public String patientRegistrationPost(Model model, @RequestParam(value="fname", required=false) String name
    		, @RequestParam(value="lname", required=false) String lastname
    		, @RequestParam(value="patr", required=false) String patro
    		, @RequestParam(value="email", required=false) String email
    		, @RequestParam(value="password", required=false) String pass1
    		, @RequestParam(value="password2", required=false) String pass2) {

		if(!pass1.equals(pass2))
    	{
    		model.addAttribute("errormessage", "Wrong password");
    		setHospitals(model);
    		return "patientregister";
    	}
		
		TPatients patient = new TPatients(name, lastname, patro, email, pass1);
		
		try{
    		patientsDAO.persist(patient);	
    	} catch(RuntimeException re){
    		model.addAttribute("errormessage", "Adding to database error! Please, try register later.");
    		return "patientregister";
    	}
		
		
    	model.addAttribute("errormessage", "Success! Patient registered");
        return "patientregister";
    }
	
    @GetMapping("/doctorregistration")
    public String doctorRegistrationGet(Model model) {
    	setHospitals(model);
    	model.addAttribute("errormessage", "");
        return "doctorregister";
    }
    
    @PostMapping("/doctorregistration")
    public String registrationPost(Model model, @RequestParam(value="fname", required=false) String name
    		, @RequestParam(value="lname", required=false) String lastname
    		, @RequestParam(value="patr", required=false) String patro
    		, @RequestParam(value="licensenumber", required=false) String licNumber
    		, @RequestParam(value="password", required=false) String pass1
    		, @RequestParam(value="password2", required=false) String pass2
    		, @RequestParam(value="hospital", required=false) String hospitalId) {
    	
    	
    	TDoctors doctor = new TDoctors(name, lastname, patro, licNumber, pass1);
    	
    	try{
    		doctorsDAO.persist(doctor);	
    	} catch(RuntimeException re){
    		if(re.getMessage().contains("license"))
    			model.addAttribute("errormessage", "Doctor with this license number already exists!");
    		else model.addAttribute("errormessage", "Adding to database error! Please, try register later.");
    		return "doctorregister";
    	}
    	
    	setHospitals(model);
    	
    	THospitalDoctors hospitalDoctor = new THospitalDoctors(doctor, hospitalDAO.findById(Integer.parseInt(hospitalId)), new Date());
    	hospitalDoctorsDAO.persist(hospitalDoctor);
    	
    	
    	model.addAttribute("errormessage", "Success! "+doctor.getDoctorId());
    	return "doctorregister";
    	
    }
    
}