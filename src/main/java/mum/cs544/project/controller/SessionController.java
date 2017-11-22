package mum.cs544.project.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mum.cs544.project.entity.Location;
import mum.cs544.project.entity.Person;
import mum.cs544.project.entity.Role;
import mum.cs544.project.entity.Session;
import mum.cs544.project.service.ILocationService;
import mum.cs544.project.service.IPersonService;
import mum.cs544.project.service.ISessionService;

@Controller
public class SessionController {

	@Autowired
	ISessionService sessionService;

	@Autowired
	IPersonService personService;
	
	@Autowired
	ILocationService locationService;

	@RequestMapping(value={"/admin/sessions"}, method = RequestMethod.GET)
	public String getAllSessions(Model model) {
		model.addAttribute("sessions", sessionService.getAllSessions());
		return "/admin/sessions";
	}

//	@RequestMapping(value = "/admin/createSession", method = RequestMethod.GET)
//	public String getAllSessions(@Valid Session session) {
//		return "createSession";
//	}

//	@RequestMapping(value = "/admin/sessions", method = RequestMethod.POST)
//	public String createSession(@Valid Session session, BindingResult result) {
//		String view = "redirect:/admin/sessions";
//		if (!result.hasErrors())
//			sessionService.createSession(session);
//		else
//			view = "createSession";
//
//		return view;
//	}

	@RequestMapping(value = "/admin/session_edit/{id}", method = RequestMethod.GET)
	public String ediPlayer(@ModelAttribute("sessionForUpdated") Session session, Model model, @PathVariable("id") Long id) {
		Session sessionForUpdated = sessionService.getSessionById(id);
		model.addAttribute("sessionForUpdated", sessionForUpdated);

		return "/admin/session_edit";
	}

	
	@RequestMapping(value = { "admin/session_edit/{id}" }, method = RequestMethod.POST)
	public String editSession(@Valid @ModelAttribute("sessionForUpdated") Session session, BindingResult bindingresult, Model model, @PathVariable("id") Long id) {
		if (bindingresult.hasErrors()) {
			model.addAttribute("id", id);
			return "/admin/session_edit";
		}
		sessionService.editSession(session,id);
		return "redirect:/admin/sessions";
	}
	
	@RequestMapping(value = "/admin/session_create", method = RequestMethod.GET)
	public String addPlayer(@ModelAttribute("addSession") Session session, Model model) {
		return "/admin/session_create";
	}
	
	@RequestMapping(value = "/admin/session_create", method = RequestMethod.POST)
	public String addPlayer(@Valid @ModelAttribute("addSession") Session session, BindingResult bindingresult, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (bindingresult.hasErrors()) 
			return "/admin/session_create";

		sessionService.addSession(session);
		redirectAttributes.addFlashAttribute("session", session);
		//redirectAttributes.addFlashAttribute("message", "Added successfully.");
		return "redirect:/admin/sessions";
	}
	
	@RequestMapping(value = "/admin/session_delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {	
		sessionService.deleteSession(id);
		return "redirect:/admin/sessions";
	}

	@ModelAttribute("locations")
	private Map<Long, String> getListOfLocation() {
		Map<Long, String> map = new HashMap<>();
		List<Location> locations = locationService.getAllLocation();
		for (Location location : locations) {
			map.put(location.getId(), location.getName());
		}
		return map;
	}

	@ModelAttribute("counselors")
	public Map<Long, String> getListOfConselor() {
		List<Person> conselors = personService.getAllConselor();
		Map<Long, String> map = new HashMap<>();

		for (Person conselor : conselors) {
			 List<Role> roles = conselor.getRoles();
			 for (Role role : roles) {
				 if (role.getName().equals("counselor"))
					 map.put(conselor.getId(), conselor.getFullName());
			 }
		}

		return map;
	}

	@ModelAttribute("times")
	private Map<Date, String> getTimes() {
		Date time1 = new Calendar.Builder()
		        .setDate(2016, 1, 1)
		        .setTimeOfDay(12, 0, 0)
		        .setLocale(Locale.UK).build()
		        .getTime();
		Date time2 = new Calendar.Builder()
		        .setDate(2016, 1, 1)
		        .setTimeOfDay(12, 15, 0)
		        .setLocale(Locale.UK).build()
		        .getTime();
		Date time3 = new Calendar.Builder()
		        .setDate(2016, 1, 1)
		        .setTimeOfDay(15, 0, 0)
		        .setLocale(Locale.UK).build()
		        .getTime();
		Date time4 = new Calendar.Builder()
		        .setDate(2016, 1, 1)
		        .setTimeOfDay(15, 30, 0)
		        .setLocale(Locale.UK).build()
		        .getTime();
		
		//Sat Mar 03 00:00:00 CST 2018
		Map<Date, String> times = new HashMap<>();
		times.put(time1, "12:00:00");
		times.put(time2, "12:15:00");
		times.put(time3, "15:00:00");
		times.put(time4, "15:30:00");
		
		return times;
	}
}
