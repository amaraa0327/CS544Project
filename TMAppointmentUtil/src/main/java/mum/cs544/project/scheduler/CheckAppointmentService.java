package mum.cs544.project.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mum.cs544.project.email.Email;
import mum.cs544.project.email.EmailService;
import mum.cs544.project.entity.Appointment;
import mum.cs544.project.entity.Person;
import mum.cs544.project.entity.Session;
import mum.cs544.project.service.IAppointmentService;
import mum.cs544.project.util.DateUtil;

@Service("checkAppointment")
public class CheckAppointmentService {
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
    private EmailService emailService;
	
	public void checkAppointments() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("Job start: " + dateFormat.format(date));
		//query all appointment by reminder = 0 (not yet trigger email to remind) 
		List<Appointment> appointments = appointmentService.getAppointmentsByReminder(0);
		if(appointments != null) {
			LocalDateTime todayAdd36Hours = LocalDateTime.now().plusHours(36);
			
			boolean flagNoEmail = true;
			Session session = null;
			Person person = null;
			for(Appointment appointment : appointments) {
				session = appointment.getSession();
				person = appointment.getPerson();
				
				if(session != null && person != null) {
					//compare date time within 36 hours
					LocalDateTime sessionDateTime = DateUtil.convertToLocalDateTime(session.getDate(), session.getTime());
					if(todayAdd36Hours.compareTo(sessionDateTime) >= 0) {
						flagNoEmail = false;
						
						//send reminder email to attendee
						System.out.println("Reminder email to [" + person.getFullName() + "]'s email [" + person.getEmail() + "]");
						Email email = new Email("tri.mum.ia@mum.edu", 
								person.getEmail(),
								person.getFullName(),
								appointment.getSession().getCounselor().getFullName(),
								appointment.getCreator().getFullName(),
								appointment.getSession().getInfo());
	       				email.setEmailType(2);
	       				emailService.sendSimpleMessage(email);
       				
	       				//send email to counselor
	       				System.out.println("Reminder email to [" + appointment.getSession().getCounselor().getFullName() + "]'s email [" + appointment.getSession().getCounselor().getEmail() + "]");
           				email.setTo(appointment.getSession().getCounselor().getEmail());
           				emailService.sendSimpleMessage(email);
	       				
						//update reminder flag
						appointment.setReminder(1);
						appointmentService.save(appointment);
					}
				}
			}
			
			if(flagNoEmail) {
				System.out.println("There is NO appointment prior 36 hours.");
			}
		}
		else {
			System.out.println("There is NO appointment available.");
		}
	}
}
