package it.h2i.idservice.accountablemodel.webservices;

public class Login{
	private String session_token;

	private String status;

	public String getSession_token() {
		return session_token;
	}

	public void setSession_token(String session_token) {
		this.session_token = session_token;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private Object object;
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
