package it.h2i.idservice.accountablemodel.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import it.h2i.idservice.accountablemodel.connection.Entity;
import it.h2i.idservice.accountablemodel.controller.SpringController;
import it.h2i.idservice.accountablemodel.model.User;





public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		Entity em=new Entity();

		User u =em.getUserByMail(request.getParameter("username"));
		if(u!=null && new BCryptPasswordEncoder().matches(request.getParameter("password"), u.getPassword())) {
			if(exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
				setDefaultFailureUrl("/loginError");
			}
			else if(exception.getClass().isAssignableFrom(AuthenticationException.class)) {
				setDefaultFailureUrl("/loginError");				
			}
			else if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {       
				setDefaultFailureUrl("/loginError");	
			}
			else if (exception.getClass().isAssignableFrom(DisabledException.class)) {    
				SpringController.mailTemp=u.getMail();
				setDefaultFailureUrl("/pageMailSend");

			} 

		}else {
			setDefaultFailureUrl("/loginError");
		}

		super.onAuthenticationFailure(request, response, exception);  
	}
}