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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

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
public class SpringController {
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



	@RequestMapping("/")
	public String index(HttpServletRequest request,HttpServletResponse response, Model model) {
		return "login";
	}
	@RequestMapping("/testList")
	public String indexs(HttpServletRequest request,HttpServletResponse response, Model model) {
		return "testList";
	}
	@RequestMapping("/forElements")
	public ModelAndView pageforElements(HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Entity e=new Entity();
		List <User> listaProva=e.getAllUser();
		e.close();
		return new ModelAndView("forElements","users",listaProva);
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
		if(flagOrg==true) {
			mavCurrent=returnViewOrganization("");
			flagOrg=false;
		}
		if(mavCurrent!=null || vistaCorrente.equals("organizatioManager")) {
			return mavCurrent;
		}else {
			return new ModelAndView("login");
		}
	}
	@RequestMapping("/viewUsersOrg")
	public String ViewUsers(@RequestParam("piva") String piva,HttpServletResponse response) {
		vistaCorrente="allUserOrganization";
		Entity e = new Entity();
		Organization o= e.getOrganization(piva);
		currentOrganization=o.getPiva();		
		currentUsers=null;
		Set appertains = e.getOrganization(currentOrganization).getAppertains();
		currentUsers= new LinkedList<User>();
		for( Object a: appertains) {
			currentUsers.add(((Appertain) a).getUser());			
		}	
		e.close();
		mavCurrent=returnViewUser("");
		return "redirect:/"+vistaCorrente;
	}

	@RequestMapping("/removeOrg")
	public String removeOrg(@RequestParam("piva") String piva,HttpServletResponse response) {
		vistaCorrente="organizationManager";
		Entity e = new Entity();
		Organization o=e.getOrganization(piva);
		if(o!=null ) {
			e.deleteAppertainByOrg(o.getIdorganization());
			e.Delete(o);
			e.close();
			mavCurrent=returnViewOrganization("Organizzazione : "+o.getName()+" con P.Iva="+o.getPiva()+", rimossa con successo");
			return "redirect:/"+vistaCorrente;
		}else {
			mavCurrent=returnViewOrganization("Error in remove");
			return "redirect:/"+vistaCorrente;

		}
	}
	@RequestMapping("/remove")
	public ModelAndView remove(@RequestParam("mail") String mail,HttpServletResponse response) {
		vistaCorrente="allUserOrganization";
		Entity e = new Entity();
		Organization o=e.getOrganization(currentOrganization);
		User u=e.getUserByMail(mail);
		if(o!=null && u!=null) {
			e.deleteAppertainByOrg(u.getIduser(),o.getIdorganization());
			e.close();
            RefreshCurrentUsersOrg();
			return new ModelAndView("forElements","users",currentUsers);
		
		}
		return new ModelAndView("forElements","users",currentUsers);




	}


	@RequestMapping("/enable")
	public String enable(@RequestParam("mail") String mail,HttpServletResponse response) {

		Entity e = new Entity();
		User user = e.getUserByMail(mail);

		if (user.getEnable()) {
			user.setEnable(false);
		} else {
			user.setEnable(true);
		}

		e.merge(user);	
		e.close();
		mavCurrent=returnViewUser("");
		return "redirect:/"+vistaCorrente;
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

	@RequestMapping(value = "/loginEffettuata", method = RequestMethod.GET)
	public ModelAndView adminPage() {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView mav=new ModelAndView("loginEffettuata");
		mav.addObject("a",auth.getName());
		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
	        System.out.println(" FUNZIONA");
			mav.addObject("ManageOrganization","<div class=\"container-login100-form-btn m-t-32\">\r\n" + 
					"\r\n" + 
					"					<a href=\"organizationManager\" class=\"login100-form-btn\">ManagementORG</a>\r\n" + 
					"				</div>");
		}else {
			System.out.println(" NON FUNZIONA");
			return mav;
		}
		return mav;
	}



	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}



	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()) {
			ModelAndView model = new ModelAndView("loginEffettuata");;

		}

		ModelAndView model = new ModelAndView("login");;
		return model;
	}
	@RequestMapping(value = "/loginError", method = RequestMethod.GET)
	public ModelAndView loginError() {

		ModelAndView model = new ModelAndView("login","errors","Parametri errati");;
		return model;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		return new ModelAndView("register");
	}

	@RequestMapping(value = "/mailResend", method = RequestMethod.GET)
	public ModelAndView MailResend(HttpServletResponse response, 
			HttpServletRequest request) {
		if(mailTemp!=null) {
			Entity e=new Entity();
			User user =e.getUserByMail(mailTemp);
			if(user!=null) {
				try {
					StringBuffer appUrl = request.getRequestURL();
					int i=0;
					if (request.getQueryString() != null) {
						appUrl.append("?").append(request.getQueryString());
					}
					String completeURL = appUrl.toString();
					StringTokenizer st= new StringTokenizer(completeURL,"/", true);		
					String appUrls = "";
					while (st.hasMoreElements() && i<7) {
						appUrls+= st.nextToken();
						i++;
					}

					Token t=user.getToken();
					String token=t.getToken();
					String recipientAddress = user.getMail();
					String subject = "Registration Confirmation";
					String confirmationUrl = ""+appUrls + "regitrationConfirm?token=" + token;
					String message=" Clicca sul link per confermare la registrazione:  ";
					String text=message + "/n "  + confirmationUrl;
					new Utility().SendJavaMail("lucah2ialfino@gmail.com", "springmvc", recipientAddress, subject, text);
				} catch (Exception me) {
					logger.error("evento",me);
					return new ModelAndView("login", "mail", "Errore invio mail");
				}
				mailTemp=null;
				return new ModelAndView("login", "mail", "mail inviata nuovamente");

			}
		}

		return new ModelAndView("login");
	}



	@RequestMapping(value = "/pageMailSend", method = RequestMethod.GET)
	public ModelAndView pageMailSend() {
		if(pageMailSendFlag) {

		}

		return new ModelAndView("pageMailSend","user","Ops! Pare che tu non abbia verificato il tuo account!");
	}
	@RequestMapping(value = "/samlListener", method = RequestMethod.POST)
	public ModelAndView samlListener(HttpServletResponse response, 
			HttpServletRequest request) throws IOException {

		String encoded=request.getParameter("SAMLResponse");

		String decode=new Utility().Base64(encoded);
		String userid=new Utility().getUserIDsaml(decode, "emailAddress");
		String status=new Utility().getStatusSaml(decode);

		Entity e=new Entity();

		User u=e.getUserByMail(userid); // se user id non è nel db.

		if(u==null) {


			String name = new Utility().getNameSaml(decode);
			String surname = new Utility().getSurnameSaml(decode);
			String password = new Utility().getPasswordSaml(decode);
			if(name!=null && surname!=null && password!=null) {
				u = new User(name,surname,userid,password);
				e.Insert(u);	
			}
			if(u!=null) {
				return new ModelAndView("samlListener","status", "<br><br><br><br><br><h2 style=\"color: white;\">Ok!</h2><h2 style=\"color: white;\">Utente registrato!</h2>");


			}else
				return new ModelAndView("samlListener","status", "Utente non presente nel DB");






		}
		Authentication auth = new UsernamePasswordAuthenticationToken(u.getMail(), null,null);
		SecurityContextHolder.getContext().setAuthentication(auth);



		mailTemp=u.getMail();

		if(u.getEnable()) {
			response.sendRedirect("loginEffettuata");
			ModelAndView mav=new ModelAndView("loginEffettuata","a",auth.getName());
			return mav;
		}
		else {
			response.sendRedirect("pageMailSend");
			return new ModelAndView("pageMailSend", "user", "Abbiamo inviato una mail all'indirizzo: " + mailTemp);
		}
	}


	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView controlla(@RequestParam String name, 
			@RequestParam String surname,
			@RequestParam String password,
			@RequestParam String mail,
			HttpServletResponse response, 
			HttpServletRequest request) throws IOException, NoSuchAlgorithmException{

		String mess="Sei stato registrato. Login: "+name;
		if(name==null ||surname==null || password==null || mail==null) {
			return new ModelAndView("register","errors","parametri errati o incompleti");
		}else if(name.length()==0 || surname.length()==0|| password.length()==0 || mail.length()==0)
			return new ModelAndView("register","errors","parametri errati o incompleti");
		Entity entity= new Entity();
		if(!entity.ValidateRegistration(mail)) {
			return new ModelAndView("register","errors","Utente gia presente");

		}
		Utility ut= new Utility();
		if(!ut.isValidPassword(password)) {
			return new ModelAndView("register","errors","La Password deve essere di almeno 6 caratteri");
		}
		if(!ut.isValidEmailAddress(mail)) {
			return new ModelAndView("register","errors","Non hai inserito un indirizzo mail valido");
		}
		password=new BCryptPasswordEncoder().encode(password);
		User registered=new User(name,surname,mail, password);
		entity.Insert(registered);

		try {
			StringBuffer appUrl = request.getRequestURL();
			int i=0;
			if (request.getQueryString() != null) {
				appUrl.append("?").append(request.getQueryString());
			}
			String completeURL = appUrl.toString();
			StringTokenizer st= new StringTokenizer(completeURL,"/", true);		
			String appUrls = "";
			while (st.hasMoreElements() && i<7) {
				appUrls+= st.nextToken();
				i++;
			}

			eventPublisher.publishEvent(new OnRegistrationCompleteEvent
					(registered, request.getLocale(), appUrls));
		} catch (Exception me) {
			logger.error("eventooooooooooooooooooooooooooooo",me);
			return new ModelAndView("register", "errors", "si e verificato un errore sull'invio della mail");
		}		
		entity.merge(registered);
		mailTemp=mail;
		return new ModelAndView("pageMailSend", "user", "Abbiamo inviato una mail all'indirizzo: " + mail);

	}
	@RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
	public ModelAndView confirmRegistration
	(WebRequest request, Model model, @RequestParam("token") String token) {


		Locale locale = request.getLocale();
		Entity en=new Entity();


		Token tok = en.getToken(token);

		if (tok == null) {
			String messageValue = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", messageValue);
			return new ModelAndView("errorPage", "errors", "errore imprevisto non esiste utente: ");
		}

		User user = tok.getUser();
		Calendar cal = Calendar.getInstance();
		if ((tok.getDate().getTime() - cal.getTime().getTime()) <= 0) {
			String messageValue = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("errors", messageValue);
			return new ModelAndView("errorPage", "errors", "errore imprevisto il tempo e scaduto");
		} 

		user.setEnable(true);
		user.resetToken();
		en.DeleteToken(tok);
		en.merge(user);
		return new ModelAndView("login", "user", "ACCOUNT ATTIVATO. PUOI FARE LA LOGIN");
	}

	public ModelAndView returnViewUser(String message) {
		ModelAndView map= new ModelAndView(vistaCorrente);
		RefreshCurrentUsersOrg();
		map.addObject("users", new Utility().ListToForm(currentUsers));
		map.addObject("error",message);
		return map;
	}
	public ModelAndView returnViewOrganization(String message) {
		ModelAndView map= new ModelAndView(vistaCorrente);
		Entity e = new Entity();
		currentOrganizations=e.getAllOrganizations();
		e.close();
		map.addObject("organizations", new Utility().ListToFormOrg(currentOrganizations));
		map.addObject("error",message);
		return map;
	}
	public void RefreshCurrentUsersOrg(){
		Entity e = new Entity();
		currentUsers=null;
		Set appertains = e.getOrganization(currentOrganization).getAppertains();
		e.close();
		currentUsers= new LinkedList<User>();
		for( Object a: appertains) {
			currentUsers.add(((Appertain) a).getUser());			
		}
	}






}