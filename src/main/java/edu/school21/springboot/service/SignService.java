package edu.school21.springboot.service;

import edu.school21.springboot.dto.SignUpInDto;
import edu.school21.springboot.exception.CinemaRuntimeException;
import edu.school21.springboot.model.User;
import edu.school21.springboot.model.type.UserRole;
import edu.school21.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class SignService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		user.getRoles().add(UserRole.ROLE_USER);
		userRepository.save(user);

		log.debug("Register new user: {}", dto.getEmail());
	}

}
