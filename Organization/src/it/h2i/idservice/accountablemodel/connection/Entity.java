package it.h2i.idservice.accountablemodel.connection;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;

import it.h2i.idservice.accountablemodel.model.Token;
import it.h2i.idservice.accountablemodel.model.User;



public class Entity {

	Connection c;


	public Entity(){
		c=new Connection();
	}


	public boolean Verify(String a, String b) throws NoSuchAlgorithmException {
		if(a==null || b==null ) {
			return false;
		}
		Utility ut=new Utility();
		b=ut.Sha1(b);
		EntityManager em=c.getEntity();
		boolean bs= !em.createQuery("From User u Where u.username='"+a+"' AND u.password='"+b+"'").getResultList().isEmpty();
		return bs;
	}

	public boolean Presente(String username) {
		if(username==null ||username.equals("")) {
			return false;
		}
		EntityManager em=c.getEntity();
		boolean b=em.createQuery("From User u Where u.username='"+username+"'").getResultList().isEmpty();
		return b;

	}
	public User DammiUtente(String username) {
		if(username==null ||username.equals("")) {
			return null;
		}
		EntityManager em=c.getEntity();
		List l=em.createQuery("From User u Where u.username='"+username+"'").getResultList();
		User u;
		if(l.isEmpty()) {
			return null;
		}else {
			u=(User) l.get(0);
		}
		
		return u;

	}
	
	public User getUserByMail(String mail) {
		if(mail==null ||mail.equals("")) {
			return null;
		}
		EntityManager em=c.getEntity();
		List l=em.createQuery("From User u Where u.mail='"+mail+"'").getResultList();
		User u;
		if(l.isEmpty()) {
			return null;
		}else {
			u=(User) l.get(0);
		}
		
		return u;

	}


	public Token getToken(String token) {
		if(token==null ||token.equals("")) {
			return null;
		}
		EntityManager em=c.getEntity();
		Token to=(Token) em.createQuery("From Token t Where t.token='"+token+"'").getResultList().get(0);
		return to;

	}
	public Boolean DeleteToken(Token token) {
		EntityManager em=c.getEntity();

		  em.getTransaction().begin();
		  em.remove(token);
		  em.getTransaction().commit();
		  return true;
		
		
		
		}
	



	public boolean ValidateRegistration(String username, String mail) {
		EntityManager em=c.getEntity();
		Boolean ret =em.createQuery("From User u Where u.username='"+username+"' OR u.mail='"+mail+"'").getResultList().isEmpty();
		return ret;


	}

	public boolean PuoInserire(String a,String b) {
		if(a==null || b==null ||b.equals("")) {
			return false;
		}
		EntityManager em=c.getEntity();
		Boolean ret =em.createQuery("From User u Where u.username='"+a+"'").getResultList().isEmpty();
		return ret;

	}
	public boolean Insert(User u) {
		EntityManager em=c.getEntity();
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		return true;	
	}
	public boolean merge(User u) {
		EntityManager em=c.getEntity();
		em.getTransaction().begin();
		em.merge(u);
		em.getTransaction().commit();
		return true;

	}	

	public boolean Insert(Token token) {
		EntityManager em=c.getEntity();
		em.getTransaction().begin();
		em.persist(token);
		em.getTransaction().commit();
		return true;	
	}
	


}
