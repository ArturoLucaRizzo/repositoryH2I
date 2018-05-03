package it.h2i.idservice.accountablemodel.webservices;

import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import it.h2i.idservice.accountablemodel.connection.Entity;
import it.h2i.idservice.accountablemodel.model.Token;
import it.h2i.idservice.accountablemodel.model.User;



@WebService
public class WebServices implements OrganizationServices{


	@WebMethod
	public Response<UserMetadata> Login(LoginRequest request) {
		LoginRequest login;
		Response r=new Response();
		String username;
		String password;
		if(request instanceof LoginRequest) {
			login=(LoginRequest) request;
			username=login.username;
			password=login.getPassword();
		}else {
			r.setStatus(UserStatus.BAD_REQUEST);
			return r;
		}



		Entity e=new Entity();
		User u=e.getUserByMail(username);



		if(u==null) {
			r.setStatus(UserStatus.BAD_REQUEST);
		}else {
			if(u.getPassword().equals(password)){
				String token=UUID.randomUUID().toString();
				r.setStatus(UserStatus.SUCCESS);
				r.setObject(new UserMetadata(u));
				r.setToken(token);
				e.setSessionToken(u, token);
				e.close();

			}else {
				r.setStatus(UserStatus.BAD_REQUEST);
			}
		}

		return r;

	}


}
