package it.h2i.idservice.accountablemodel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.h2i.idservice.accountablemodel.connection.Entity;
import it.h2i.idservice.accountablemodel.model.User;



@Service
public class MyUserDetailsService implements UserDetailsService {


	private Entity entity;

	@Override
	public UserDetails loadUserByUsername(String username) {
		entity=new Entity();
		if(username==null || username.isEmpty()) {
			new UsernameNotFoundException(username);
		}
	
			User u=entity.DammiUtente(username);
			if(u!=null)// se l'utente ha confermato la mail ritorna l'user details dell'utente.
				return new MyUserPrincipal(u);
		
		return (UserDetails) new UsernameNotFoundException(username);
		

	}
}