package edu.school21.springboot.security;

import edu.school21.springboot.repository.UserRepository;
import edu.school21.springboot.support.TransactionalHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final TransactionalHelper transactionalHelper;

	@Override //anna@school21.ru
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return transactionalHelper.executeReadOnly(() -> {
			return userRepository.findByEmail(username)
					.map(CustomUserDetails::new)
					.orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
		});
	}

}
