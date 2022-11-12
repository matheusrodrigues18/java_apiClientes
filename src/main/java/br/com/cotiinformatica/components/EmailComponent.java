package br.com.cotiinformatica.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {

	@Autowired
	JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	String username;

	public void mailSend(String email, String assunto, String corpo) throws Exception {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(username);
		mailMessage.setTo(email);
		mailMessage.setSubject(assunto);
		mailMessage.setText(corpo);
		
		mailSender.send(mailMessage);
		
	}
	
}
