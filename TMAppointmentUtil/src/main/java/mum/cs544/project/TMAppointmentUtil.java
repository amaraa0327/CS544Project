package mum.cs544.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TMAppointmentUtil {

	/*@Autowired
    private EmailService emailService;
	
	//need to implements ApplicationRunner
	@Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
		ST subject = new ST("TM Checking Appointment on <date>!");
		subject.add("date", new Date());
		ST content = new ST("Dear <name>!\n\n" +
				"You have booked a TM Checking appointment at <date> successfully.");
		content.add("name", "Tri");
		content.add("date", new Date());
		
        Email mail = new Email();
        mail.setFrom("no-reply@gmail.com");
        mail.setTo("trint.it@gmail.com");
        //mail.setSubject(subject.render());
        //mail.setContent(content.render());

        //emailService.sendSimpleMessage(mail);
    }*/
	
	public static void main(String[] args) {
		SpringApplication.run(TMAppointmentUtil.class, args);
	}
}
