package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.utils.Constants;

@Service
public class EmailService {

	public static final String ACTIVATION_TITLE = "Bank account activation";
	public static final String ACTIVATION_TEXT = "Dear %s %s, \n\n"
			+ "In order to activate your bank account, please click the following link: \n"
			+ Constants.BACKEND + "/auth/activate/%s\n"
			+ "We advise you to change your password the first time you sign in.\n"
			+ "Initial password: " + Constants.INITIAL_PASSWORD + "\n\n"
			+ "Best regards, \n"
			+ "Your banking system.";

	@Autowired
	private JavaMailSenderImpl sender;
	
	@Async
	public void sendEmail(Email email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getText());
		this.sender.send(message);
	}
	
}
