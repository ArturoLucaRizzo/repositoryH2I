package it.h2i.idservice.accountablemodel.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import it.h2i.idservice.accountablemodel.model.User;


public interface OrganizationServices {
	
	
	public Response Login(String username, String password);

}
