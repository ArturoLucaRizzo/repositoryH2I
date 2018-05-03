package it.h2i.idservice.accountablemodel.webservices;

import javax.annotation.Resource;

import it.h2i.idservice.accountablemodel.model.User;

public class UserMetadata extends Response{
	
	
	String name;
	String surname;
	
	String mail;
	
	public UserMetadata(String name, String surname, String mail) {
		super();
		this.name = name;
		this.surname = surname;
		this.mail = mail;
	}
	public UserMetadata(User u) {
		name=u.getName();
		surname=u.getSurname();
		mail=u.getMail();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

}
