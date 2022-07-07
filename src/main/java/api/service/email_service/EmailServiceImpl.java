package api.service.email_service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {
	
	@Value("${email.properties.path}")
	private String pathToProp;
	
	private Properties prop;
	
	public EmailServiceImpl() {
	}

	public void sendEmail(  String messageToSend, String receiver) {
		prop = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + pathToProp);
			prop.load(fis);
			String username = prop.getProperty("mail.smtp.user");
			String password = prop.getProperty("mail.smtp.password");
			Session  session = Session.getInstance(prop, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication(username, password );

	            }
			}) ;
//			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			
            message.setFrom(new InternetAddress(username));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            message.setSubject("verification code");

            message.setText(messageToSend);

            Transport.send(message);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}

	@Override
	public String generateCode() {
		String code ="";
		for(int x = 0; x<9 ; x++) {
			int i = ThreadLocalRandom.current().nextInt(65, 119);
			code+=(char)i;
		}
		return code;
	}

}
