package it.h2i.idservice.accountablemodel.connection;
import javax.persistence.*;

public class Connection {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	
	public Connection() {
		super();
		this.emf = Persistence.createEntityManagerFactory("myPersistence");
		this.em = emf.createEntityManager();
	}
	public EntityManager getEntity() {
		return em;
	}
	public void close() {
		em.clear();
		em.close();
	}
	
	
	
	

}
