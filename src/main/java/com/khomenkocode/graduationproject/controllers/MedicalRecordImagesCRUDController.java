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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.khomenkocode.graduationproject.dao.TDoctorsDAO;
import com.khomenkocode.graduationproject.dao.TImagesDAO;
import com.khomenkocode.graduationproject.dao.TMedicalRecordsDAO;
import com.khomenkocode.graduationproject.dao.TPatientsDAO;
import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TImages;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;
import com.khomenkocode.graduationproject.services.GoogleDriveService;

@Controller
@SessionAttributes({"patient","admin","doctor", "driveAccessCode", "recordnum", "drive", "oauthredirect"})
public class MedicalRecordImagesCRUDController {
	
	@Autowired
	TPatientsDAO patientsDAO;

	@Autowired
	TDoctorsDAO doctorsDAO;

	@Autowired
	TMedicalRecordsDAO medicalRecordsDAO;
	
	@Autowired
	TImagesDAO imagesDAO;
	
	
	@GetMapping("/callback")
    public String googleCallback(Model model, @RequestParam(value = "code") String code, HttpSession httpSession){
		model.addAttribute("driveAccessCode", code);
		System.out.println(code);
		
		if(((int)httpSession.getAttribute("oauthredirect")) == 1)
			return "redirect:/licenseconfirmation";
		
		return "redirect:/addimage?record="+httpSession.getAttribute("recordnum");
	}	
	
	@GetMapping("/addimage")
    public String patientAddImage(Model model, @RequestParam(value = "record") String record, HttpSession httpSession) {
		model.addAttribute("oauthredirect", 0);
		if(httpSession.getAttribute("driveAccessCode")==null){
    		model.addAttribute("records", true);
    		model.addAttribute("recordnum", record);
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
        		model.addAttribute("recordnum", record);
            	model.addAttribute("errormessage", "");
        		return "googleoauth";
    		}
    	}
    	
    	TMedicalRecords medRec;
    	try{
    		medRec = medicalRecordsDAO.findById(Integer.parseInt(record));
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return "redirect:/records";
    	}
    	model.addAttribute("medrecord", medRec);
		model.addAttribute("records", true);
    	model.addAttribute("errormessage", "");
        return "addimage";
    }
	
	
	@PostMapping(path = "/addimage", consumes = { "multipart/form-data" })
    public String patientAddImagePost(Model model,
    		HttpSession httpSession, 
    		@RequestParam(value = "record") String record, 
    		@RequestParam(value ="password", required = false) String password,
    		@RequestParam("imgname") String name,
    		@RequestParam("image") MultipartFile multipartFile) {
    	try{
    		
    		
    		TPatients pat = (TPatients) httpSession.getAttribute("patient");
    		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");

    		TMedicalRecords medRec = medicalRecordsDAO.findById(Integer.parseInt(record));
    		
    		if(httpSession.getAttribute("admin")!=null){
    			
    		} else if(password == null){ 
    			model.addAttribute("errormessage", "Error! No password.");
    			return "addimage";
    		} else if(doc!=null && !medRec.getTpatients().getPassword().equals(password)) {
    	    	model.addAttribute("errormessage", "wrong password");
    			return "addimage";
    		} else if(pat!=null && !medRec.getTdoctors().getPassword().equals(password)) {
    	    	model.addAttribute("errormessage", "wrong password");
    			return "addimage";
    		}
    		
    		Drive service = (Drive) httpSession.getAttribute("drive");
    		String googleDriveLink = GoogleDriveService.saveImageAndGetLink(multipartFile, service);
    		TImages image = new TImages(medRec, name, googleDriveLink);
    		
    		imagesDAO.persist(image);
    		return "redirect:/records";
    	}	catch(NoResultException e){  
    		return "redirect:/records";
        }
    }
	
	@GetMapping("/removeimage")
    public String patientDeleteImage(Model model, @RequestParam(value = "id") String imageId, HttpSession httpSession) {
		
		if(httpSession.getAttribute("patient") != null){
			TImages image = imagesDAO.findById(Integer.parseInt(imageId));
			imagesDAO.remove(image);
			return "redirect:/records";
		}
		
		TImages image = imagesDAO.findById(Integer.parseInt(imageId));
		model.addAttribute("medrecord", image.getTmedicalRecords());
		model.addAttribute("records", true);
    	model.addAttribute("errormessage", "");
    	
		return "deleteimage";
	}
	
	@PostMapping("/removeimage")
    public String patientDeleteImagePost(Model model,
    		HttpSession httpSession,
    		@RequestParam(value = "id") String imageId, 
    		@RequestParam("password") String password) {

		TPatients pat = (TPatients) httpSession.getAttribute("patient");
		TDoctors doc = (TDoctors) httpSession.getAttribute("doctor");
		TImages image = imagesDAO.findById(Integer.parseInt(imageId));
		
		if(doc!=null && !image.getTmedicalRecords().getTpatients().getPassword().equals(password)){
	    	model.addAttribute("errormessage", "wrong password");
	    	model.addAttribute("medrecord", image.getTmedicalRecords());
			model.addAttribute("records", true);
			return "removeimage";
		}
		if(pat!=null && !image.getTmedicalRecords().getTdoctors().getPassword().equals(password))
		{
			model.addAttribute("medrecord", image.getTmedicalRecords());
			model.addAttribute("records", true);
	    	model.addAttribute("errormessage", "wrong password");
			return "removeimage";
		}
		
		imagesDAO.remove(image);
		return "redirect:/records";
	}
}
