package it.h2i.idservice.accountablemodel.connection;

import java.nio.charset.StandardCharsets;
import java.security.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.h2i.idservice.accountablemodel.model.Organization;
import it.h2i.idservice.accountablemodel.model.User;

public class Utility {

	public String Sha1(String password) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	public boolean isValidEmailAddress(String mail) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
		return matcher.find();

	}

	public boolean isValidPassword(String password) {

		if (password.length() < 6) {
			return false;
		}
		return true;
	}
	public String ListToForm(List<User> list) {
		String field=null;
		int i=0;
		if(list!=null) {
			field="";
			for(User u : list) {
				i++;
				field+=	"      <tr>\r\n" + 
						"        <td class='id' style='display:none;'>"+i+"</td>\r\n" + 
						"        <td class='name' name='name' style='color:white;'>"+u.getName()+"</td>\r\n" + 
						"        <td class='surname'name='surname' style='color:white;'>"+u.getSurname()+"</td>\r\n" + 
						"        <td class='mail'name='mail' style='color:white;'>"+u.getMail()+"</td>\r\n" + 
						"        <td class='edit'><button class=\"edit-item-btn\">Edit</button></td>\r\n" + 
						"        <td class='remove'> <a href='remove?mail="+u.getMail()+"'style='color: red;'>Remove</a></td>\r\n" + 
						"        <td style=color: white;>"+((u.getEnable() ? "<a href='enable?mail="+u.getMail()+
								"' style='color: red;'>Disable</a>" : "<a href='enable?mail="+u.getMail()+"' style='color: green;'>Enable</a>\r\n"))+
						"      </tr>\r\n";

			}

		}
		return field;
	}
	public String ListToFormOrg(List<Organization> list) {
		String field=null;
		int i=0;
		if(list!=null) {
			field="";
			for(Organization o : list) {
				i++;
				field+=	"      <tr>\r\n" + 
						"        <td class='id' style='color:white;'>"+o.getIdorganization()+"</td>\r\n" + 
						"        <td class='organization' name='organization' style='color:white;'>"+o.getName()+"</td>\r\n" + 
						"        <td class='piva'name='piva' style='color:white;'>"+o.getPiva()+"</td>\r\n" + 
						"        <td style='color:white;'>"+o.getAppertains().size()+"</td>\r\n" + 
						"        <td class='edit'><button class=\"edit-item-btn\">Edit</button></td>\r\n" + 
						"        <td class='remove'> <a href='removeOrg?piva="+o.getPiva()+"'style='color: red;'>Remove</a></td>\r\n" + 
						"        <td class='view'> <a href='viewUsersOrg?piva="+o.getPiva()+"'style='color: green;'>View</a></td>\r\n" +
						"      </tr>\r\n";

			}

		}
		return field;
	}
	public String Base64(String encoded) {
		byte[] decoded = Base64.getDecoder().decode(encoded);
		return new String(decoded, StandardCharsets.UTF_8);

	}

	public String getUserIDsaml(String decoded, String formatUserId) {

		StringTokenizer st = new StringTokenizer(decoded, ":><");

		while (st.hasMoreElements()) {
			String parse = st.nextToken();
			String sub = "";
			if (formatUserId.length() < parse.length()) {
				sub = parse.substring(0, formatUserId.length());
			}
			if (sub.equals(formatUserId)) {

				if (st.hasMoreElements()) {

					return st.nextToken();

				}

			}

		}
		return null;

	}

	public String getNameSaml(String decoded) {

		String formatUserId = "Name=" + '"' + "name" + '"';

		StringTokenizer st = new StringTokenizer(decoded);

		while (st.hasMoreElements()) {

			String parse = st.nextToken();
			String sub = "";
			if (formatUserId.length() < parse.length()) {

				sub = parse.substring(0, formatUserId.length());

			}

			if (sub.equals(formatUserId)) {

				if (st.hasMoreElements()) {

					String name = st.nextToken();

					int i = name.indexOf(">") + 1;
					int f = name.indexOf("<");

					String n = name.substring(i, f);

					return n;

				}

			}

		}
		return null;
	}

	public String getSurnameSaml(String decoded) {

		String formatUserId = "Name=" + '"' + "surname" + '"';

		StringTokenizer st = new StringTokenizer(decoded);

		while (st.hasMoreElements()) {

			String parse = st.nextToken();
			String sub = "";
			if (formatUserId.length() < parse.length()) {

				sub = parse.substring(0, formatUserId.length());

			}

			if (sub.equals(formatUserId)) {

				if (st.hasMoreElements()) {

					String name = st.nextToken();

					int i = name.indexOf(">") + 1;
					int f = name.indexOf("<");

					String n = name.substring(i, f);

					return n;

				}

			}

		}
		return null;
	}

	public String getPasswordSaml(String decoded) {

		String formatUserId = "Name=" + '"' + "password" + '"';

		StringTokenizer st = new StringTokenizer(decoded);

		while (st.hasMoreElements()) {

			String parse = st.nextToken();
			String sub = "";
			if (formatUserId.length() < parse.length()) {

				sub = parse.substring(0, formatUserId.length());

			}

			if (sub.equals(formatUserId)) {

				if (st.hasMoreElements()) {

					String name = st.nextToken();

					int i = name.indexOf(">") + 1;
					int f = name.indexOf("<");

					String n = name.substring(i, f);

					PasswordEncoder pe = new BCryptPasswordEncoder();
					String password = pe.encode(n);

					return password;

				}

			}

		}
		return null;
	}

	public String getStatusSaml(String decoded) {
		StringTokenizer st = new StringTokenizer(decoded, ":>/<");
		String temp = "";
		while (st.hasMoreElements()) {
			String parse = st.nextToken();
			String sub = "";
			if ("status".length() <= parse.length()) {
				sub = parse.substring(0, "status".length());
			}
			if (sub.equals("status") && temp.equals("2.0")) {

				if (st.hasMoreElements()) {
					String last = st.nextToken();

					return last.substring(0, last.length() - 1);

				}

			}
			temp = parse;
		}

		return null;

	}

	public void SendJavaMail(String sourceAddress, String passwordSourceAddress, String destinationAddress,
			String subject, String mess) {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sourceAddress, passwordSourceAddress);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sourceAddress));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationAddress));
			message.setSubject(subject);
			message.setText(mess);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
