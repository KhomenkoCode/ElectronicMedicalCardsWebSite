package com.khomenkocode.graduationproject.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.Drive;
import com.khomenkocode.graduationproject.dao.TDoctorsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TImages;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;
import com.khomenkocode.graduationproject.services.GoogleDriveService;

@Controller
@SessionAttributes({"patient","admin","doctor", "driveAccessCode", "recordnum", "drive", "oauthredirect"})
public class ProfileContorller {
	
	@Autowired
	TDoctorsDAO doctorsDAO;
	
	@Autowired
	TPatientsDAO patientsDAO;
	
	
	@GetMapping("/profile")
	public String profileGet(Model model, HttpSession httpSession) {


		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");
		if(doc != null){
			if(doc.getDoctorIsConfirmed() == 1)
				model.addAttribute("inprocess", true);

			if(doc.getDoctorIsConfirmed() == 2)
				model.addAttribute("confirmed", true);
		}
		
		
		return "profile";
    }
	
	
	@GetMapping("/changeprofileinfo")
	public String changeProfileGet(Model model, HttpSession httpSession) {
		
		if(httpSession.getAttribute("admin") != null){
			return "redirect:/adminpanel";
		}

    	model.addAttribute("errormessage", "");
		return "changeprofileinfo";
    }

	@Transactional
	@PostMapping("/changeprofileinfo")
	public String changeProfilePost(Model model, HttpSession httpSession
    		, @RequestParam(value="lname") String lastname
    		, @RequestParam(value="patr", required=false) String patro
    		, @RequestParam(value="password") String pass
    		, @RequestParam(value="bloodgroup", required=false) String blood
    		, @RequestParam(value="rh", required=false) String rh
    		, @RequestParam(value="allergies", required=false) String allergies) {
		
		
		if(httpSession.getAttribute("admin") != null){
			return "redirect:/adminpanel";
		}

		TPatients pat = (TPatients) httpSession.getAttribute("patient");
		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");
		
		if(pat != null){
			if(!pat.getPassword().equals(pass))
			{
				model.addAttribute("errormessage", "Wrong password!");
				return "changeprofileinfo";
			}
			
			pat.setPatientSurname(lastname);
			pat.setPatientPatronymic(patro);
			pat.setPatientBloodGroup(blood);
			pat.setPatientRhFactor(rh);
			pat.setPatientAllergiesInfo(allergies);
			patientsDAO.merge(pat);
		}
		else if(doc != null){
			if(!doc.getPassword().equals(pass))
			{
				model.addAttribute("errormessage", "Wrong password!");
				return "changeprofileinfo";
			}
			
			doc.setDoctorSurname(lastname);
			doc.setDoctorPatronymic(patro);
			doctorsDAO.merge(doc);
		}
		
    	model.addAttribute("errormessage", "");
		return "redirect:/profile";
    }
	
	@GetMapping("/licenseconfirmation")
	public String licenseConfirmationGet(Model model, HttpSession httpSession) {
		model.addAttribute("oauthredirect", 1);
		if(httpSession.getAttribute("patient") != null){
			return "redirect:/profile";
		}
		
		if(httpSession.getAttribute("admin") != null){
			return "redirect:/adminpanel";
		}

		if(httpSession.getAttribute("driveAccessCode")==null){
    		model.addAttribute("records", true);
        	model.addAttribute("errormessage", "");
    		return "googleoauth";
    	}
    	
    	if(httpSession.getAttribute("drive")==null)
    	{
    		String code = (String) httpSession.getAttribute("driveAccessCode");
    		Drive service = GoogleDriveService.getService(code);
    		if(service != null)
    			model.addAttribute("drive", service);
    		else {
    			model.addAttribute("records", true);
            	model.addAttribute("errormessage", "");
        		return "googleoauth";
    		}
    	}
		
    	
    	model.addAttribute("errormessage", "");
		return "licenseconfirmation";
    }
	
	@PostMapping(path = "/licenseconfirmation", consumes = { "multipart/form-data" })
    public String patientAddImagePost(Model model,
    		HttpSession httpSession, 
    		@RequestParam("image") MultipartFile multipartFile)  {
		
		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");

	
		Drive service = (Drive) httpSession.getAttribute("drive");
		String googleDriveLink = GoogleDriveService.saveImageAndGetLink(multipartFile, service);
		
		doc.setLicenseConfirmationPhotoUrl(googleDriveLink);
		doc.setDoctorIsConfirmed((short) 1);
		
		doctorsDAO.merge(doc);
		
		return "redirect:/profile";
	}
}
