package it.h2i.idservice.accountablemodel.webservices;

public class Response<T> {
	private T t;
	private UserStatus status;
	private String message;
	private String token;
	private Object object;

	
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Object getObject() {
		return object;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}



}

