package it.h2i.idservice.accountablemodel.model;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Token {
    private static final int EXPIRATION = 60 * 24;
 
    @Id
    private int idtoken;
    @Column(nullable = false, name = "token")
    private String token;
   
    @OneToOne
    @JoinColumn(name = "idtoken")
    private User user;
    @Column(nullable = false, name = "date")
    private Date date;
    
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	public Token(User user,String token) {
		super();
		this.user=user;
		this.idtoken=user.getIduser();
		this.token = token;
		date=calculateExpiryDate(EXPIRATION);
	}

	public Token() {
		super();
		date=calculateExpiryDate(EXPIRATION);

	}

	public int getIdtoken() {
		return idtoken;
	}

	public void setIdtoken(int idtoken) {
		this.idtoken = idtoken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}
     
    // standard constructors, getters and setters
}