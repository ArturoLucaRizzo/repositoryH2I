package it.h2i.idservice.accountablemodel.model;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="token")
public class Token {
    private static final int EXPIRATION = 60 * 24;
 
    
    private int idtoken;
    private String token;
    private User user;
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

	@Id
	public int getIdtoken() {
		return idtoken;
	}

	public void setIdtoken(int idtoken) {
		this.idtoken = idtoken;
	}

	@Column(nullable = false, name = "token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

    @OneToOne
    @JoinColumn(name = "idtoken")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @Column(nullable = false, name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}
     
    
}