package it.h2i.idservice.accountablemodel.model;



import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.springframework.aop.ThrowsAdvice;

import net.bytebuddy.implementation.bytecode.Throw;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
,catalog="organization"
, uniqueConstraints = @UniqueConstraint(columnNames="mail") 
		)
public class User  implements java.io.Serializable {


	@Override
	public int hashCode() {
		return getIduser();
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer iduser;
	private String name;
	private String surname;
	private String mail;
	private String password;
	private String role;
	







	@Column(name="role", nullable=false, length=45)
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}



	private List<Appertain> appertains = new LinkedList();
	
	private List<Session_Token> session_token = new LinkedList();

	private Token token; 

	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user2")
	public List<Session_Token> getSession_token() {
		return session_token;
	}


	public void setSession_token(List<Session_Token> session_token) {
		this.session_token = session_token;
	}



	private boolean enable;


	@Column(name="enable", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean getEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	public Token getToken() {
		return token;
	}


	public void setToken(Token token) {
		this.token = token;
	}


	public User() {
		this.enable=false;
	}


	public User(String name, String surname, String mail, String password) {
		this.name = name;
		this.surname = surname;
		this.mail = mail;
		this.password = password;
		this.enable=false;
		this.role="USER";
	}
	public User(String name, String surname, String mail, String password, List<Appertain> appertains) {
		this.name = name;
		this.surname = surname;
		this.mail = mail;
		this.password = password;
		this.appertains = appertains;
		this.role="USER";
		this.enable=false;
	}

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="iduser", unique=true, nullable=false)
	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	@Column(name="name", nullable=false, length=45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="surname", nullable=false, length=45)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(name="mail", unique=true, nullable=false, length=45)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name="password", nullable=false, length=95)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="user")
	public List<Appertain> getAppertains() {
		return (List<Appertain>) this.appertains;
	}

	public void setAppertains(List<Appertain> appertains) {
		this.appertains = appertains;
	}

	public void resetToken() {
		token=null;
	}
	
	



}


