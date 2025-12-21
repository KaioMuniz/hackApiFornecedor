package br.hackathon.com.application.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailSenderComponent {

	@Autowired
	JavaMailSender javaMailSender;

	public void send(String to, String subject, String body, boolean isHtml) {

		try {

			var message = javaMailSender.createMimeMessage();
			var helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, isHtml);

			javaMailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
