package com.khomenkocode.graduationproject.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.khomenkocode.graduationproject.dao.TDoctorsDAO;
import com.khomenkocode.graduationproject.dao.THospitalsDAO;
import com.khomenkocode.graduationproject.dao.TMedicalRecordsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.THospitals;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
@SessionAttributes({"patient", "admin", "doctor"})
public class AdminPanelController {
	@Autowired
	TPatientsDAO patientsDAO;

	@Autowired
	TDoctorsDAO doctorsDAO;

	@Autowired
	TMedicalRecordsDAO medicalRecordsDAO;
	
	@Autowired
	THospitalsDAO hospitalsDAO;
	
	 @GetMapping("/adminpanel")
	 public String adminPanelGet(Model model, HttpSession httpSession) {
		if(httpSession.getAttribute("admin") == null)
			return "redirect:/";
	   	model.addAttribute("adminpanel", true);
	    return "adminpanel";
	 }
	 

	 @PostMapping("/adminpanel")
	 public String adminPanelPost(Model model, 
			 HttpSession httpSession, 
			 @RequestParam(value="email") String email, 
			 @RequestParam(value="Submit") String submit) {
		
		 if(httpSession.getAttribute("admin") == null)
			 return "redirect:/";
		 if(email.equals(""))
			 return "adminpanel";

		 model.addAttribute("patient", patientsDAO.findByEmail(email));
		 	
		 if(submit.equals("Records"))
			 return "redirect:/recordsbypatient?email="+email;
		 else return "redirect:/changepatientprofile?email="+email;
	 }
	 
	 @GetMapping("/recordsbypatient")
	 public String recordsByPatientGet(Model model, HttpSession httpSession,@RequestParam(value = "email") String email) {
		 if(httpSession.getAttribute("admin") == null)
			 return "redirect:/";
		 
		 TPatients patient = patientsDAO.findByEmail(email);
		 List<TMedicalRecords> medicalRecords = medicalRecordsDAO.findAllByPatient(patient);
		 model.addAttribute("records", medicalRecords);
		 model.addAttribute("medicalrecords", true);
		 return "medicalrecords";
     }
	 
	 @GetMapping("/changepatientprofile")
	 public String changePatientProfileGet(Model model, HttpSession httpSession,@RequestParam(value = "email") String email) {
		 if(httpSession.getAttribute("admin") == null)
			 return "redirect:/";
		 if(email.equals(""))
			 return "adminpanel";

		 model.addAttribute("adminpanel", true);
		 model.addAttribute("patient", patientsDAO.findByEmail(email));
	     model.addAttribute("errormessage", "");
		 return "changeprofileinfo";
     }
	 
	 	@Transactional
		@PostMapping("/changepatientprofile")
		public String changePatientProfilePost(Model model, HttpSession httpSession,
				@RequestParam(value="fname") String firstname,
	    		@RequestParam(value="lname") String lastname,
	    		@RequestParam(value="patr", required=false) String patro,
	    		@RequestParam(value="bloodgroup", required=false) String blood,
	    		@RequestParam(value="rh", required=false) String rh,
	    		@RequestParam(value="allergies", required=false) String allergies) {
			
			
			if(httpSession.getAttribute("admin") == null){
				return "redirect:/adminpanel";
			}

			TPatients pat = (TPatients) httpSession.getAttribute("patient");
			
			if(pat != null){	
				pat.setPatientName(firstname);
				pat.setPatientSurname(lastname);
				pat.setPatientPatronymic(patro);
				pat.setPatientBloodGroup(blood);
				pat.setPatientRhFactor(rh);
				pat.setPatientAllergiesInfo(allergies);
				patientsDAO.merge(pat);
			}

	    	model.addAttribute("errormessage", "");
			return "redirect:/profile";
	    }
	 	
	 	@GetMapping("/changedoctorprofile")
		 public String changeDoctorProfileGet(Model model, 
				 HttpSession httpSession, 
				 @RequestParam(value = "license") String license) {
			 
	 		 if(httpSession.getAttribute("admin") == null)
				 return "redirect:/";
			 if(license.equals(""))
				 return "adminpanel";

			 model.addAttribute("adminpanel", true);
			 model.addAttribute("doctor", doctorsDAO.findByLicenseNumber(license));
		     model.addAttribute("errormessage", "");
			 return "changeprofileinfo";
	     }
	 	
	 	@Transactional
		@PostMapping("/changedoctorprofile")
		public String changeDoctorProfilePost(Model model, HttpSession httpSession,
				@RequestParam(value="fname") String firstname,
				@RequestParam(value="lname") String lastname,
	    		@RequestParam(value="patr", required=false) String patro) {
			
			
			if(httpSession.getAttribute("admin") == null){
				return "redirect:/adminpanel";
			}

			TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");
			
			if(doc != null){	
				doc.setDoctorName(firstname); 
				doc.setDoctorSurname(lastname);
				doc.setDoctorPatronymic(patro);
				doctorsDAO.merge(doc);
			}

	    	model.addAttribute("errormessage", "");
			return "redirect:/profile";
	    }
	 	
	 	@GetMapping("/doctoracceptlist")
		public String doctorAcceptListGet(Model model,
				HttpSession httpSession) {
			 
	 		if(httpSession.getAttribute("admin") == null)
	 			return "redirect:/";

			model.addAttribute("adminpanel", true);
			model.addAttribute("doctors", doctorsDAO.findAllUnconfirmed());
			return "doctoracceptlist";
	    }
	 	
	 	
	 	@PostMapping("/doctoracceptlist")
		public String doctorAcceptListPost(Model model,
				HttpSession httpSession, 
				 @RequestParam(value="Submit") String submit,
				 @RequestParam(value = "ln") String licenseNumber) {
			 
	 		if(httpSession.getAttribute("admin") == null)
	 			return "redirect:/";

	 		TDoctors doc = (TDoctors) doctorsDAO.findByLicenseNumber(licenseNumber);
	 		
	 		if(submit.equals("Confirm")){
	 			doc.setDoctorIsConfirmed((short)2);
	 		}else{
	 			doc.setDoctorIsConfirmed((short)0);
	 		}
	 		doctorsDAO.merge(doc);
	 		
			return "redirect:/doctoracceptlist";
	    }
	 	
	 	@GetMapping("/hospitallist")
		public String hospitalListGet(Model model,
				HttpSession httpSession) {
			 
	 		if(httpSession.getAttribute("admin") == null)
	 			return "redirect:/";
	 		
	 		List<THospitals> hosp = hospitalsDAO.getAll();
	 		model.addAttribute("hospitals", hosp);
			model.addAttribute("adminpanel", true);
			model.addAttribute("doctors", doctorsDAO.findAllUnconfirmed());
			return "hospitallist";
	    }
	 	
	 	@GetMapping("/addhospital")
		public String addHospitalGet(Model model,
				HttpSession httpSession) {
			 
	 		if(httpSession.getAttribute("admin") == null)
	 			return "redirect:/";

	    	model.addAttribute("errormessage", "");
			model.addAttribute("adminpanel", true);
			return "addhospital";
	    }
}
