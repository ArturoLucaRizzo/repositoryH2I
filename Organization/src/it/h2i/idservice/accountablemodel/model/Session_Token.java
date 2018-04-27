package it.h2i.idservice.accountablemodel.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="session_token"
,catalog="organization")
public class Session_Token {
	
	String idsession_token;
	
	Date date;
	
	User user2;
	
	public Session_Token(String token) {
		this.idsession_token=token;
		this.date= new Date();
		
	}
	
	@Id 
	@Column(name="idsession_token", unique=true, nullable=false)
	public String getIdsession_token() {
		return idsession_token;
	}

	public void setIdsession_token(String idsession_token) {
		this.idsession_token = idsession_token;
	}
	
	@Column(nullable = false, name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="iduser_session_token", nullable=false)
	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}
	
	
	
	
	

}
