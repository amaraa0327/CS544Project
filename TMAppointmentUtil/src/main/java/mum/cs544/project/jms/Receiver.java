package mum.cs544.project.jms;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import mum.cs544.project.email.Email;
import mum.cs544.project.email.EmailService;
import mum.cs544.project.entity.Appointment;
import mum.cs544.project.service.IAppointmentService;

@Component
public class Receiver {

	/*@Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }*/
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
    private EmailService emailService;
	
	@JmsListener(destination = "${spring.jms.template.default-destination}")
    public void receiveMessage(String data) {
        System.out.println("Received data [" + data + "]");
        String group, sFlag, sAppointmentID;
       	StringTokenizer strToken = new StringTokenizer(data, "|");
       	group = strToken.nextToken();
       	sFlag = strToken.nextToken();
       	sAppointmentID = strToken.nextToken();
       	
       	if(group != null && sFlag != null && sAppointmentID != null) {
       		if(group.equals("ETHER")) {
       			try {
       				int flag = Integer.parseInt(sFlag);
           			long appointmentID = Long.parseLong(sAppointmentID);
           			
           			Appointment appointment = appointmentService.findOne(appointmentID);
           			Email email = null;
           			
           			if(appointment != null) {
           				String attendee = appointment.getPerson().getFullName();
           				String counselor = appointment.getSession().getCounselor().getFullName();
           				String creator = appointment.getCreator().getFullName();
           				
           				//send email to attendee
           				email = new Email("noreply@mum.edu", 
									appointment.getPerson().getEmail(),
									attendee,
									counselor,
									creator,
									appointment.getSession().getInfo());
           				email.setEmailType(flag);
           				emailService.sendSimpleMessage(email);
           				
           				//send email to counselor
           				email.setTo(appointment.getSession().getCounselor().getEmail());
           				emailService.sendSimpleMessage(email);
           				
           				//send email to creator, only if the creator is different with attendee
           				if(appointment.getCreator().getId() != appointment.getPerson().getId()) {
	           				email.setTo(appointment.getCreator().getEmail());
	           				emailService.sendSimpleMessage(email);
           				}
           				
           				System.out.println("Finished sending email.");
           			}
           			else {
           				System.out.println("Appointment NOT found with ID [" + appointmentID + "]");
           			}
       			}
       			catch(NumberFormatException nfe) {
       				System.out.println("Invalid JMS Message data received [" + data + "]");
       			}
       		}
       		else {
       			System.out.println("Invalid JMS Message format received [" + data + "]");
       		}
       	}
       	else {
       		System.out.println("Invalid JMS Message received [" + data + "]");
       	}
    }

}
