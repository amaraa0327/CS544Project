package mum.cs544.project.email;

import org.stringtemplate.v4.ST;

public class Email {
	private ST stSubject = new ST("<action> TM Checking Session!");
	private ST stContent = new ST("Appointment information:\n\n" +
										"\tAttendee: <attendee>\n" + 
										"\tCounselor: <counselor>\n" + 
										"\tCreator: <creator>\n" + 
										"\tSession: <session>\n\n" +
										"This is an auto email system. Please do not reply!");
	
    private String from;
    private String to;
    private String subject;
    private String content;
    private int emailType; //1: new, 0: cancel, 2: remind
    private String attendee;
    private String counselor;
    private String creator;
    private String session;
    
    public Email() { }
    public Email(String from, String to, String attendee, String counselor, String creator, String session) {
        this.from = from;
        this.to = to;
        this.attendee = attendee;
        this.counselor = counselor;
        this.creator = creator;
        this.session = session;
    }
    
    public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getAttendee() {
		return attendee;
	}
	public void setAttendee(String attendee) {
		this.attendee = attendee;
	}
	public String getCounselor() {
		return counselor;
	}
	public void setCounselor(String counselor) {
		this.counselor = counselor;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getSubject() {
        return subject;
    }
    public String getContent() {
        return content;
    }
    public int getEmailType() {
		return emailType;
	}
	public void setEmailType(int emailType) {
		this.emailType = emailType;
		
		//build subject
		switch(this.emailType) {
		case 0: //cancel session
			stSubject.add("action", "Cancel");
			break;
		case 1: //new session
			stSubject.add("action", "New");
			break;
		case 2: //cancel session
			stSubject.add("action", "Remind");
			break;
		}
		stSubject.add("session", this.session);
		this.subject = stSubject.render();
		
		//build content
		stContent.add("attendee", this.attendee);
		stContent.add("counselor", this.counselor);
		stContent.add("creator", this.creator);
		stContent.add("session", this.session);
		this.content = stContent.render();
	}
	
    @Override
    public String toString() {
        return "Mail{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
    
}
