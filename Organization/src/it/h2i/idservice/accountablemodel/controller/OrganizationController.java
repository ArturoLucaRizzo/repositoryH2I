package it.h2i.idservice.accountablemodel.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import it.h2i.idservice.accountablemodel.DTO.ActionDTO;
import it.h2i.idservice.accountablemodel.DTO.AddDTO;
import it.h2i.idservice.accountablemodel.connection.Entity;
import it.h2i.idservice.accountablemodel.connection.Utility;
import it.h2i.idservice.accountablemodel.model.Appertain;
import it.h2i.idservice.accountablemodel.model.AppertainId;
import it.h2i.idservice.accountablemodel.model.Organization;
import it.h2i.idservice.accountablemodel.model.Role;
import it.h2i.idservice.accountablemodel.model.Token;
import it.h2i.idservice.accountablemodel.model.User;
import it.h2i.idservice.accountablemodel.security.OnRegistrationCompleteEvent;


@Controller
public class OrganizationController {
	final static Logger logger = Logger.getLogger(SpringController.class); 
	public static String mailTemp=null;
	boolean pageMailSendFlag=true;
	boolean flagOrg=true;
	String vistaCorrente="";
	String currentOrganization;
	List<Organization> currentOrganizations;
	ModelAndView mavCurrent;
	List<User> currentUsers;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	User user;

	private MessageSource messages;



	@RequestMapping("/forElements")
	public ModelAndView pageforElements(HttpServletRequest request,HttpServletResponse response, Model model) {

		RefreshCurrentUsersOrg();
		return new ModelAndView("forElements","users",currentUsers);
	}

	@RequestMapping("/forUsers")
	public ModelAndView pageforListUsers(HttpServletRequest request,HttpServletResponse response, Model model) {


		Entity e = new Entity();
		List<User> users = e.getAllUser();
		e.close();
		List<User> us = new ArrayList();
		boolean userPresent;
		for (int i = 0; i < users.size(); i++) {
			userPresent = false;
			for (int j = 0; j < currentUsers.size(); j++) {
				if (users.get(i).getMail().equals(currentUsers.get(j).getMail())) {
					userPresent = true;
				}
			}

			if (!userPresent) {
				us.add(users.get(i));
			}

		}

		return new ModelAndView("forUsers", "users", us);


	}
	@RequestMapping("/forElementsOrganizations")
	public ModelAndView pageforElementsOrganizations(HttpServletRequest request,HttpServletResponse response, Model model) {
		Entity e=new Entity();
		currentOrganizations=e.getAllOrganizations();
		e.close();

		return new ModelAndView("forElementsOrganizations","organizations",currentOrganizations);
	}


	@RequestMapping(value="/addAndEdit", method = RequestMethod.POST)
	public @ResponseBody AddDTO addEditButtonOrg(@RequestBody AddDTO rs) {

		if(rs.getThird_parameter().equals("organization")) {


			String piva = rs.getSecond_parameter();
			String nameOrg = rs.getFirst_parameter();

			Entity e = new Entity();
			Organization o = e.getOrganization(piva);

			if(o == null) {
				o=new Organization();
				o.setName(nameOrg);
				o.setPiva(piva);
				e.Insert(o);
				e.close();

				return new AddDTO("ok",null,null,"add",null);

			}else {

				return new AddDTO("not ok",null,"presente",null,null);
			}




		}else if(rs.getThird_parameter().equals("user")) {


			Entity e = new Entity();
			User u= e.getUserByMail(rs.getFirst_parameter());
			if(u!=null) {
				Appertain a =new Appertain();
				Organization o= e.getOrganization(currentOrganization);
				Role r= e.getRoleById(1);
				a.setId(new AppertainId(u.getIduser(),o.getIdorganization()));
				a.setRole(r);
				a.setOrganization(o);
				a.setUser(u);
				u.getAppertains().add(a);
				e.merge(u);
				e.close();
				return new AddDTO("ok","add",null,null,null);

			}else {


				return new AddDTO("not ok",null,null,null,null);
			}


		}	

		return new AddDTO("not ok",null,null,null,null);


	}








	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public @ResponseBody AddDTO EditButton(@RequestBody AddDTO rs) {
		String type= rs.getFour_parameter();
		if(type.equals("organizations")) {
			String idchar=rs.getFirst_parameter();
			String name= rs.getSecond_parameter();
			String piva= rs.getThird_parameter();
			if(piva==null || name==null || idchar==null) {

				return new AddDTO("not ok",type,"errore nell'inserimento dei dati");

			}else {

				if(idchar==null || !new Utility().isInteger(idchar)) {
					System.out.println("PIVA "+piva);
					System.out.println("name "+name);
					System.out.println("idchar "+idchar);
					return new AddDTO("not ok",type,"errore nell'inserimento dei dati");
				}
				
				int id=Integer.parseInt(idchar);
				Organization o=null;
				for( Organization org: currentOrganizations) {
					if(org.getIdorganization()==id) {
						o=org;
					}
					if(org.getPiva().equals(piva)) {
						return new AddDTO("not ok",type,"questa partita iva è gia assegnata ad altra organizzazione");
						
					}
				}	

				if(o!=null) {
					if(o.getName().equals(name) && o.getPiva().equals(piva)) {
						return new AddDTO("not ok",type,"modofiche inconsistenti");

					}
					// controllo appartenenza organizzazione
					o.setName(name);
					o.setPiva(piva);
					Entity e = new Entity();
					e.merge(o);
					e.close();
					//aggiorna currentuserS degli utenti apparteneti alle org;
					return new AddDTO("ok",type,"Organizzazione aggiornata con successo");

				}else {
					return new AddDTO("not ok",type,"Organizzazione non trovata nel db");

				}

			}

		}else if(type.equals("users")){
			String mail= rs.getThird_parameter();
			String name=rs.getFirst_parameter();
			String surname=rs.getSecond_parameter();
			if(mail==null || !new Utility().isValidEmailAddress(mail)) {
				return new AddDTO("not ok",type,"MailErrata");
			}

			User u=null;
			for( User user: currentUsers) {
				if(user.getMail().equals(mail)) {
					u=user;
				}
			}	

			if(u!=null && name!=null && surname!=null) {
				if(u.getName().equals(name) && u.getSurname().equals(surname)) {
					return new AddDTO("not ok",type,"nessuna modifica rilevata");
				}
				// controllo appartenenza organizzazione
				Entity e = new Entity();
				u.setName(name);
				u.setSurname(surname);
				u.setMail(mail);
				e.merge(u);
				e.close();
				//aggiorna currentuserS degli utenti apparteneti alle org

				return new AddDTO("ok",type,"Utente "+ mail + " aggiornato");

			}else {
				return new AddDTO("not ok",type,"modifiche errate");

			}


		}
		return new AddDTO("not ok",type,"nessun tipo rilveto");




	}


	@RequestMapping("/organizationManager")
	public ModelAndView organizations() {
		vistaCorrente="organizationManager";

		return returnViewOrganization(null);
	}


	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public @ResponseBody ActionDTO Remove(@RequestBody ActionDTO rs) {
		vistaCorrente="allUserOrganization";
		if(rs.getParameter()!=null) {
			Entity e = new Entity();
			if(new Utility().isValidEmailAddress((rs.getParameter()))) {
				Organization o=e.getOrganization(currentOrganization);
				User u=e.getUserByMail(rs.getParameter());
				if(o!=null && u!=null) {
					try {
						e.deleteAppertainByOrg(u.getIduser(),o.getIdorganization());
						e.close();
						RefreshCurrentUsersOrg();
						return new ActionDTO("ok","user");
					}catch(Exception ex) {
						e.close();
						return new ActionDTO("not ok","user");
					}

				}else {
					return new ActionDTO("not ok","user");
				}

			}else {
				Organization o=e.getOrganization(rs.getParameter());
				if(o!=null ) {
					try {

						e.deleteAppertainByOrg(o.getIdorganization());
						e.Delete(o);
						currentOrganizations=e.getAllOrganizations();
						e.close();
						return new ActionDTO("ok","organization");
					}catch(Exception ex){
						e.close();
						return new ActionDTO("not ok","organization");

					}

				}else {
					return new ActionDTO("not ok","organization");
				}

			}
		}else {
			return new ActionDTO("not ok","organization");
		}
	}

	@RequestMapping(value="/view", method = RequestMethod.POST)
	public @ResponseBody ActionDTO View(@RequestBody ActionDTO rs) {

		Entity e = new Entity();
		currentOrganization= rs.getParameter();

		return new ActionDTO("ok","view");
	}

	@RequestMapping(value="/enable", method = RequestMethod.POST)
	public @ResponseBody ActionDTO Enable(@RequestBody ActionDTO rs) {

		Entity e = new Entity();
		User user = e.getUserByMail(rs.getParameter());

		if (user.getEnable()) {
			user.setEnable(false);
		} else {
			user.setEnable(true);
		}

		e.merge(user);	
		e.close();
		return new ActionDTO("ok","enable");
	}


	@RequestMapping("/allUser")
	public ModelAndView allUser() {
		Entity e = new Entity();
		ModelAndView map = new ModelAndView("allUser");
		currentUsers=null;
		currentUsers= new LinkedList<User>();
		currentUsers.addAll(e.getAllUser());
		map.addObject("users", currentUsers);
		vistaCorrente="allUser";
		return map;

	}
	@RequestMapping("/allUserOrganization")
	public ModelAndView allUserOrganization() {
		vistaCorrente="allUserOrganization";
		flagOrg=true;
		if(mavCurrent!=null && currentOrganization!=null) {
			return mavCurrent;
		}else {
			return new ModelAndView("login");
		}

	}



	public ModelAndView returnViewUser(String message) {
		ModelAndView map= new ModelAndView(vistaCorrente);
		RefreshCurrentUsersOrg();
		return map;
	}
	public ModelAndView returnViewOrganization(String message) {
		ModelAndView map= new ModelAndView(vistaCorrente);
		Entity e = new Entity();
		currentOrganizations=e.getAllOrganizations();
		e.close();
		return map;
	}
	public void RefreshCurrentUsersOrg(){
		Entity e = new Entity();
		currentUsers=null;
		List appertains = e.getOrganization(currentOrganization).getAppertains();
		e.close();
		currentUsers= new LinkedList<User>();
		for( Object a: appertains) {
			currentUsers.add(((Appertain) a).getUser());			
		}




	}






}