package edu.school21.springboot.service;

import edu.school21.springboot.dto.SignUpInDto;
import edu.school21.springboot.event.RegisterUserEvent;
import edu.school21.springboot.exception.CinemaRuntimeException;
import edu.school21.springboot.model.User;
import edu.school21.springboot.model.type.UserRole;
import edu.school21.springboot.model.type.UserStatus;
import edu.school21.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
public class SignService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public void signUp(SignUpInDto dto) {
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new CinemaRuntimeException("User with specified email already exists", HttpStatus.BAD_REQUEST.value());
		}

		User user = new User();
		user.setEmail(dto.getEmail());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setPhone(dto.getPhone());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setStatus(UserStatus.REGISTERED);
		user.getRoles().add(UserRole.ROLE_USER);
		userRepository.save(user);

		applicationEventPublisher.publishEvent(new RegisterUserEvent(user.getId()));

		log.debug("Register new user: {}", dto.getEmail());
	}

	@Transactional
	public void confirm(UUID token) {
		User user = userRepository.findByConfirmToken(token)
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));

		user.setStatus(UserStatus.ACTIVE);
		user.setConfirmToken(null);

		log.debug("Confirm user: {}", user.getEmail());
	}

}
