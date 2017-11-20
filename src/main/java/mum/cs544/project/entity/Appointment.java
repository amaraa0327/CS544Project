package mum.cs544.project.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

@Entity
public class Appointment {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name="session_id")
	private Session session;
	
	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;
	
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	public Appointment() { }
	public Appointment(Session session, Person person, Date createdDate) {
		super();
		this.session = session;
		this.person = person;
		this.createdDate = createdDate;
	}
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getId() {
		return id;
	}
}