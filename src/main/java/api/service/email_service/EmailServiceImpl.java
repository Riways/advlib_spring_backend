package api.service.email_service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

	Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Value("${email.properties.path}")
	private String pathToProp;

	private Properties prop;

	public EmailServiceImpl() {
	}

	public void sendEmail(String messageToSend, String receiver) {
		prop = new Properties();
		ClassLoader classLoader = null;
		classLoader = getClass().getClassLoader();
		InputStream streamWithProp =  classLoader.getResourceAsStream(pathToProp);
		try {
			prop.load(streamWithProp);
			String username = prop.getProperty("mail.smtp.user");
			String password = prop.getProperty("mail.smtp.password");
			Session session = Session.getInstance(prop, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {

					return new PasswordAuthentication(username, password);

				}
			});
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(username));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

			message.setSubject("verification code");

			message.setText(messageToSend);

			Transport.send(message);
			logger.debug("code sent to" + receiver);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			try {
				streamWithProp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String generateCode() {
		String code = "";
		for (int x = 0; x < 9; x++) {
			int i = ThreadLocalRandom.current().nextInt(65, 119);
			code += (char) i;
		}
		return code;
	}

}
