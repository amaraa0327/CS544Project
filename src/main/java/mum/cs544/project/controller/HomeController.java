package mum.cs544.project.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mum.cs544.project.model.User;

@Controller
public class HomeController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String welcome() {		
		return "home";
	}
	
	@RequestMapping(value="/access_denied", method=RequestMethod.GET)
	public String accessDenied() {		
		return "access_denied";
	}
	
	@RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String afterLogin(Model model) {
		System.out.println("welcome.....");
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			switch(grantedAuthority.getAuthority()) {
			case "ROLE_ADMIN":
				return "redirect:/admin/sessions";
			case "ROLE_COUNSELOR":
				return "redirect:/counselor/facultyPage";
			case "ROLE_CUSTOMER":
				return "redirect:/customer/createappointment";
			}
		}  
        return "home";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@ModelAttribute("user") User user, Model model, String error, String logout) {
		if (error != null)
            model.addAttribute("errorMsg", "Your username and password is invalid.");

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            return "home";
        }
        
        return "login";
    }
}
