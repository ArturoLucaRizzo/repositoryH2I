package it.h2i.idservice.accountablemodel.webservices;

public class LoginRequest extends Request{

	String username;
	String password;


	public void setLogin(String username, String password) {
		this.username=username;
		this.password=password;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
