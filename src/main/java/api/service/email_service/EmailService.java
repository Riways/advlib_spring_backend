package api.service.email_service;

public interface EmailService {

	public void sendEmail(String messageToSend, String receiver);
	
	public String generateCode(); 
}
