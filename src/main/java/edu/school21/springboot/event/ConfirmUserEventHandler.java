package edu.school21.springboot.event;

import edu.school21.springboot.exception.CinemaRuntimeException;
import edu.school21.springboot.manager.MailManager;
import edu.school21.springboot.manager.TemplateManager;
import edu.school21.springboot.model.User;
import edu.school21.springboot.model.type.UserRole;
import edu.school21.springboot.model.type.UserStatus;
import edu.school21.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Async
@Slf4j
@Service
public class ConfirmUserEventHandler {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private TemplateManager templateManager;

	@Transactional
	@TransactionalEventListener(fallbackExecution = true)
	public void onRegisterUserEvent(RegisterUserEvent event) {
		User user = userRepository.findById(event.getUserId())
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));

		if (user.getRoles().contains(UserRole.ROLE_ADMIN)) {
			user.setStatus(UserStatus.ACTIVE);
			log.debug("Confirmation is not required for user {} due to one is administrator", user.getEmail());
		} else {
			UUID token = UUID.randomUUID();

			user.setConfirmToken(token);
			user.setStatus(UserStatus.NOT_CONFIRMED);

			Map<String, Object> data = new HashMap<>();
			data.put("lastName", user.getLastName());
			data.put("firstName", user.getFirstName());
			data.put("token", token);
			String text = templateManager.renderHtml("confirm-email.ftl", data);

			mailManager.sendMessage(user.getEmail(), "Email confirmation", text);

			log.debug("Confirmation mailing was sent to {}", user.getEmail());
		}

	}

}
