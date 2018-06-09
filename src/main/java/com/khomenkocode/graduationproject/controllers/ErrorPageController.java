package com.khomenkocode.graduationproject.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"patient","admin","doctor"})
public class ErrorPageController implements ErrorController  {
	
	@RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		model.addAttribute("error code",status.toString());
		if(Integer.parseInt(status.toString()) == 404)
			model.addAttribute("error message", "Page not found");
		else if(Integer.parseInt(status.toString()) > 500)
			model.addAttribute("error message", "Server error");
		else model.addAttribute("error message", "Bad request");
		return "error";
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
