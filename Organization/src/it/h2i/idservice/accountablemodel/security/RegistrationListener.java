package it.h2i.idservice.accountablemodel.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.*;

import it.h2i.idservice.accountablemodel.connection.Utility;
import it.h2i.idservice.accountablemodel.model.Token;
import it.h2i.idservice.accountablemodel.model.User;



@Component
public class RegistrationListener implements
ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private MessageSource messages;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		if(event.isFlag()) {	
			User user = event.getUser();
			String token = UUID.randomUUID().toString();
			Token t=new Token(user,token);
			user.setToken(t);        
			String recipientAddress = user.getMail();
			String subject = "Registration Confirmation";
			String confirmationUrl = ""+event.getAppUrl() + "regitrationConfirm?token=" + token;
			String message=" il messaggio e questo Link: ";
			String text=message + "/n "  + confirmationUrl;
			new Utility().SendJavaMail("lucah2ialfino@gmail.com", "springmvc", recipientAddress, subject, text);
			event.setFlag(false);
		}
	}
}