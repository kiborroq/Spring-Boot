package edu.school21.springboot.security;

import edu.school21.springboot.model.User;
import edu.school21.springboot.model.type.UserRole;
import edu.school21.springboot.model.type.UserStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final Long id;
	private final String email;
	private final String password;
	private final boolean enabled;
	private final List<GrantedAuthority> authorities;

	public CustomUserDetails(User user) {
		id = user.getId();
		email = user.getEmail();
		password = user.getPassword();
		enabled = user.getStatus() == UserStatus.ACTIVE || user.getRoles().contains(UserRole.ROLE_ADMIN);
		authorities = user.getRoles().stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
