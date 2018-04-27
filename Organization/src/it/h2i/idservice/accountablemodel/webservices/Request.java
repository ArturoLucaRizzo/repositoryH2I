package it.h2i.idservice.accountablemodel.webservices;

public abstract class Request {
	String session_token;
	public String getSession_token() {
		return session_token;
	}
	public void setSession_token(String session_token) {
		this.session_token = session_token;
	}

	

}
