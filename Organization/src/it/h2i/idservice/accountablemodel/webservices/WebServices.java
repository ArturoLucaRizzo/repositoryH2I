package it.h2i.idservice.accountablemodel.webservices;

import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebService;

import it.h2i.idservice.accountablemodel.connection.Entity;
import it.h2i.idservice.accountablemodel.model.Token;
import it.h2i.idservice.accountablemodel.model.User;

@WebService
public class WebServices implements OrganizationServices{

	@WebMethod
	public Response Login(String username, String password) {
		
        
		Response r=new Response();

			Entity e=new Entity();
			User u=e.getUserByMail(username);

			

			if(u==null) {
				r.setStatus("invalid");
			}else {
				if(u.getPassword().equals(password)){
					String token=UUID.randomUUID().toString();
					r.setStatus("VALID");
					r.setObjcet(u,token);
					e.setSessionToken(u, token);
					e.close();
					
				}else {
					r.setStatus("invalid");
				}
			}

		return r;

	}

}
