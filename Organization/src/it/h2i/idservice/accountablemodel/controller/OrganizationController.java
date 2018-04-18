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
	@RequestMapping("/forElementsOrganizations")
	public ModelAndView pageforElementsOrganizations(HttpServletRequest request,HttpServletResponse response, Model model) {
		Entity e=new Entity();
		currentOrganizations=e.getAllOrganizations();
		e.close();
		if(currentOrganizations!=null) {
		     System.out.println("ci sono: " +currentOrganizations.size());
			
		}else
			System.out.println("perchè è null?");
  
		return new ModelAndView("forElementsOrganizations","organizations",currentOrganizations);
	}



	@RequestMapping(value="/addAndEditOrg", method = RequestMethod.POST)
	public String addeditButtonOrg(HttpServletRequest request,
			HttpServletResponse response) {
		vistaCorrente="organizationManager";
		String idchar=request.getParameter("idfield");

		if(request.getParameter("editfield")!=null) {
			if(idchar==null || !new Utility().isInteger(idchar)) {
				mavCurrent=returnViewOrganization("Error");
				return "redirect:/"+vistaCorrente;
			}
			int id=Integer.parseInt(idchar);
			String organization=request.getParameter("organizationfield");
			String piva=request.getParameter("pivafield");
			Organization o=null;
			for( Organization org: currentOrganizations) {
				if(org.getIdorganization()==id) {
					o=org;
				}
			}	

			if(o!=null && organization!=null && piva!=null) {
				if(o.getName().equals(organization) && o.getPiva().equals(piva)) {
					mavCurrent=returnViewOrganization("nessuna Modifica rilevata");
					return "redirect:/"+vistaCorrente;
				}
				// controllo appartenenza organizzazione
				o.setName(organization);
				o.setPiva(piva);
				Entity e = new Entity();
				e.merge(o);
				e.close();
				//aggiorna currentuserS degli utenti apparteneti alle org

				mavCurrent=returnViewOrganization("");
				return "redirect:/"+vistaCorrente;

			}else {
				mavCurrent=returnViewOrganization("Modifiche errate");
				return "redirect:/"+vistaCorrente;

			}

		}else {
			//add button della view 
			String piva=request.getParameter("pivafield");
			if(piva==null || piva.equals("")) {
				mavCurrent=returnViewOrganization("piva errata");
				return "redirect:/"+vistaCorrente;
			}
			for( Organization org: currentOrganizations) {
				if(org.getPiva().equals(piva)) {
					mavCurrent=returnViewOrganization("utente gia presente");
					return "redirect:/"+vistaCorrente;
				}
			}		
			String organization=request.getParameter("organizationfield");
			if(organization==null || organization.equals("")) {
				mavCurrent=returnViewOrganization("nome organizzazione errato");
				return "redirect:/"+vistaCorrente;
			}
			Entity e = new Entity();
			Organization o= new Organization();
			o.setName(organization);
			o.setPiva(piva);
			e.Insert(o);
			e.close();
			mavCurrent=returnViewOrganization("Organizzazione inserita con successo");
			return "redirect:/"+vistaCorrente;


		}

	}
	@RequestMapping(value="/addAndEdit", method = RequestMethod.POST)
	public String addeditButton(HttpServletRequest request,
			HttpServletResponse response) {
		vistaCorrente="allUserOrganization";

		String mail=request.getParameter("mailfield");
		Entity e = new Entity();
		if(mail==null || !new Utility().isValidEmailAddress(mail)) {
			mavCurrent=returnViewUser("Mail Errata");
			return "redirect:/"+vistaCorrente;
		}else if(request.getParameter("editfield")!=null) {//edit button della view
			String name=request.getParameter("namefield");
			String surname=request.getParameter("surnamefield");
			User u=null;
			for( User user: currentUsers) {
				if(user.getMail().equals(mail)) {
					u=user;
				}
			}	

			if(u!=null && name!=null && surname!=null) {
				if(u.getName().equals(name) && u.getSurname().equals(surname)) {
					mavCurrent=returnViewUser("Nessuna modifica rilevata");
					return "redirect:/"+vistaCorrente;
				}
				// controllo appartenenza organizzazione
				u.setName(name);
				u.setSurname(surname);
				u.setMail(mail);
				e.merge(u);
				e.close();
				//aggiorna currentuserS degli utenti apparteneti alle org

				mavCurrent=returnViewUser("");
				return "redirect:/"+vistaCorrente;

			}else {
				mavCurrent=returnViewUser("Modifiche errate");
				return "redirect:/"+vistaCorrente;

			}

		}else {
			//add button della view 
			User u= e.getUserByMail(mail);
			if(u!=null) {
				for(User user:currentUsers) {
					if(user.getMail().equals(mail)) {
						e.close();
						mavCurrent=returnViewUser("Utente gia presente");
						return "redirect:/"+vistaCorrente;

					}
				}
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
				mavCurrent=returnViewUser("Utente Inserito in "+ o.getName());
				return "redirect:/"+vistaCorrente;
			}else {
				mavCurrent=returnViewUser("Nessun utente trovato con la mail inserita");
				return "redirect:/"+vistaCorrente;
			}


		}

	}

	@RequestMapping("/organizationManager")
	public ModelAndView organizations() {
		vistaCorrente="organizationManager";
	
		return returnViewOrganization(null);
	}
	@RequestMapping("/viewUsersOrg")
	public String ViewUsers(@RequestParam("piva") String piva,HttpServletResponse response) {
		vistaCorrente="allUserOrganization";
		Entity e = new Entity();
		Organization o= e.getOrganization(piva);
		currentOrganization=o.getPiva();		
		currentUsers=null;
		List appertains = e.getOrganization(currentOrganization).getAppertains();
		currentUsers= new LinkedList<User>();
		for( Object a: appertains) {
			currentUsers.add(((Appertain) a).getUser());			
		}	
		e.close();
		mavCurrent=returnViewUser("");
		return "redirect:/"+vistaCorrente;
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
		mavCurrent=returnViewUser("");
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