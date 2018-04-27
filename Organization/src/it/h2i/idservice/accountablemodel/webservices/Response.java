package it.h2i.idservice.accountablemodel.webservices;

import it.h2i.idservice.accountablemodel.model.User;

public class Response {
	private String status;
	private String message;
	private Object object;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getObject() {
		return object;
	}
	public void setObjcet(Object object, String token) {
		if (object instanceof User) {
			this.object=new User_Metadata((User)object, token);
			

		}else {
			this.object=new Object_Metadata(object, token);
		}

	}
	
	


}
class Object_Metadata{
	
	Object object;;
	String token;
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	Object_Metadata(Object o, String token){
		object=o;
		this.token=token;

	}

	

}

class User_Metadata{
	
	String name;
	String surname;
	String mail;
	String token;
	
	User_Metadata(User u,String token){
		name=u.getName();
		surname=u.getSurname();
	    mail=u.getMail();
	    this.token=token;
		
	}
	User_Metadata(User u){
		name=u.getName();
		surname=u.getSurname();
	    mail=u.getMail();
	    this.token=token;
		
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
