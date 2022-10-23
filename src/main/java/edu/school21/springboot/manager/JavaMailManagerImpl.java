package edu.school21.springboot.manager;

import edu.school21.springboot.exception.CinemaRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
class JavaMailManagerImpl implements MailManager {

	@Value("${edu.school21.spring-boot.sender-email}")
	private String from;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendMessage(String to, String subject, String text) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
			helper.setTo(to);
			helper.setFrom(from);
			helper.setSubject(subject);
			helper.setText(text, true);

			javaMailSender.send(message);

			log.debug("Message was sent to {} from {}", to, from);
		} catch (Exception e) {
			throw new CinemaRuntimeException("Error during mail send", e);
		}
	}

}
