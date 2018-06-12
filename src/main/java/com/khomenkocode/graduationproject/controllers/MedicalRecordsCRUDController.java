package com.khomenkocode.graduationproject.controllers;

import java.util.Date;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.khomenkocode.graduationproject.dao.TDoctorsDAO;
import com.khomenkocode.graduationproject.dao.TMedicalRecordsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;

@Controller
@SessionAttributes({"patient","admin","doctor"})
public class MedicalRecordsCRUDController {
	
	@Autowired
	TPatientsDAO patientsDAO;

	@Autowired
	TDoctorsDAO doctorsDAO;
	
	@Autowired
	TMedicalRecordsDAO medicalRecordsDAO;
	
	@GetMapping("/addrecord")
    public String patientAddRecord(Model model) {
    	model.addAttribute("records", true);
    	model.addAttribute("errormessage", "");
        return "addrecord";
    }
    
    @PostMapping("/addrecord")
    public String patientAddRecordPost(Model model, HttpSession httpSession, 
    		@RequestParam(value = "email", required = false) String email, 
    		@RequestParam(value = "license", required = false) String license, 
    		@RequestParam(value = "password", required = false) String password,
    		@RequestParam("record") String record) {
    	try{
    		TPatients pat = (TPatients) httpSession.getAttribute("patient");
    		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");
    		
    		if(httpSession.getAttribute("admin")!=null)
    		{
    			TDoctors doctor = doctorsDAO.findByLicenseNumber(license);
    			TMedicalRecords medRec = new TMedicalRecords(doctor, pat, record, new Date());
    			medicalRecordsDAO.persist(medRec);
    		} else if(password == null){
    			model.addAttribute("errormessage", "Do not forget to enter password!");
        		return "addrecord";
    		} else if(pat != null) {
    			TDoctors doctor = doctorsDAO.findByLicenseNumberAndPassword(license, password);
    			TMedicalRecords medRec = new TMedicalRecords(doctor, pat, record, new Date());
    			medicalRecordsDAO.persist(medRec);
    		} else if(doc != null) {
    			TPatients patient = patientsDAO.findByEmailAndPassword(email, password);
    			TMedicalRecords medRec = new TMedicalRecords(doc, patient, record, new Date());
    			medicalRecordsDAO.persist(medRec);
    		}
    		
    		return "redirect:/records";
    	}	catch(NoResultException e){        
	    	model.addAttribute("errormessage", "Wrong password!");
    		return "addrecord";
        }
        
    }
    
    
    @GetMapping("/changerecord")
    public String patientChangeRecord(Model model, @RequestParam(value = "record", required = false) String record) {
    	TMedicalRecords medRec;
    	try{
    		medRec = medicalRecordsDAO.findById(Integer.parseInt(record));
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return "redirect:/records";
    	}
    	model.addAttribute("records", true);
    	model.addAttribute("record", medRec);
    	model.addAttribute("errormessage", "");
        return "changerecord";
    }
    
    @PostMapping("/changerecord")
    public String patientChangeRecordPost(Model model, HttpSession httpSession, 
    		@RequestParam(value = "record", required = true) String recordId, 
    		@RequestParam(value = "password", required = false) String password,
    		@RequestParam("recordtext") String record) {
    	try{
    		TMedicalRecords medRec = medicalRecordsDAO.findById(Integer.parseInt(recordId));

    		
    		if(httpSession.getAttribute("admin") != null){
    			medRec.setMedicalRecord(record);
    			medicalRecordsDAO.merge(medRec);
    			return "redirect:/records";
    		}
        	
    		if(password==null)
    		{     
    	    	model.addAttribute("errormessage", "Error! No password.");
        		return "changerecord";
    		}
    		
    		TPatients pat = (TPatients) httpSession.getAttribute("patient");
    		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");
    		
    		
    		if(pat != null)
    		{	
    			if(!medRec.getTdoctors().getPassword().equals(password))
    			{
    				model.addAttribute("errormessage", "Wrong password");
    				return "changerecord";
    			}
    		} else if (doc != null){
    			if(!medRec.getTpatients().getPassword().equals(password))
    			{
    				model.addAttribute("errormessage", "Wrong password");
    				return "changerecord";
    			}
    		}
    		TMedicalRecords medRec1 = new TMedicalRecords(Integer.parseInt(recordId), medRec.getTdoctors(), medRec.getTpatients(), record, medRec.getMedicalRecordDate());
    		medicalRecordsDAO.merge(medRec1);
    		return "redirect:/records";
    	}	catch(NoResultException e){        
	    	model.addAttribute("errormessage", "Error changing record. Try again later.");
    		return "changerecord";
        }
        
    }
    
    @GetMapping("/removerecord")
    public String patientRemoveRecord(Model model, @RequestParam(value = "record", required = false) String record,
    		HttpSession httpSession) {
    	TMedicalRecords medRec;
    	
    	try{
    		medRec = medicalRecordsDAO.findById(Integer.parseInt(record));
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return "redirect:/records";
    	}
    	
    	if(httpSession.getAttribute("admin") != null){
    		medicalRecordsDAO.remove(medRec);
			return "redirect:/records";
		}
    	
    	model.addAttribute("records", true);
    	model.addAttribute("record", medRec);
    	model.addAttribute("errormessage", "");
        return "removerecord";
    }
    
    @PostMapping("/removerecord")
    public String patientRemoveRecordPost(Model model, HttpSession httpSession, 
    		@RequestParam(value = "record", required = true) String recordId, 
    		@RequestParam("password") String password) {
    	try{
    		TMedicalRecords medRec = medicalRecordsDAO.findById(Integer.parseInt(recordId));
    		
    		TPatients pat = (TPatients) httpSession.getAttribute("patient");
    		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");
    		if(pat != null)
    		{
    		if(!medRec.getTdoctors().getPassword().equals(password))
    		{
    	    	model.addAttribute("errormessage", "Wrong password");
    			return "changerecord";
    		} else if (doc != null){
    			if(!medRec.getTpatients().getPassword().equals(password))
			{
				model.addAttribute("errormessage", "Wrong password");
				return "changerecord";
			}
    		}
    		}
    		medicalRecordsDAO.remove(medRec);
    		return "redirect:/records";
    	}	catch(NoResultException e){        
	    	model.addAttribute("errormessage", "Error deleting password");
    		return "removerecord";
        }
    }
}
