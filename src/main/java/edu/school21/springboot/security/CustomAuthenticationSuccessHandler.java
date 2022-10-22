package edu.school21.springboot.security;

import edu.school21.springboot.exception.CinemaRuntimeException;
import edu.school21.springboot.model.User;
import edu.school21.springboot.model.UserSession;
import edu.school21.springboot.repository.UserRepository;
import edu.school21.springboot.support.TransactionalHelper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final UserRepository userRepository;
	private final TransactionalHelper transactionalHelper;

	@Transactional
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		transactionalHelper.execute(this::addSession);

		if (response.isCommitted()) {
			return;
		}

		response.sendRedirect(SecurityUtils.getRedirectUrl());
	}

	private void addSession() {
		User user = userRepository.findById(SecurityUtils.getCurrentUserId())
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));

		UserSession session = new UserSession();
		session.setIp(SecurityUtils.getCurrentUserIpAddress());
		session.setDateTimeCreate(LocalDateTime.now());

		user.getSessions().add(session);

		log.debug("Sign in user {} from {}", user.getEmail(), session.getIp());
	}
}
