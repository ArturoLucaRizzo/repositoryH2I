package it.h2i.idservice.accountablemodel.connection;

import java.security.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

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
	public boolean  isValidEmailAddress(String mail) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = 
				Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(mail);
		return matcher.find();	

	}
	public boolean isValidPassword(String password) {

		if(password.length()<6) {
			return false;
		}
		return true;
	}
	public void SendJavaMail(String sourceAddress, String passwordSourceAddress, String destinationAddress, String subject, String mess) {


		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sourceAddress, passwordSourceAddress);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sourceAddress));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destinationAddress));
			message.setSubject(subject);
			message.setText(mess);			


			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}


