package edu.school21.springboot.security;

import edu.school21.springboot.model.type.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

	private static final Map<UserRole, String> URL_BY_ROLE;

	static {
		URL_BY_ROLE = new HashMap<>();
		URL_BY_ROLE.put(UserRole.ROLE_ADMIN, "/admin/panel/halls");
		URL_BY_ROLE.put(UserRole.ROLE_USER, "/profile");
	}

	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && CustomUserDetails.class.isAssignableFrom(authentication.getPrincipal().getClass());
	}

	public static CustomUserDetails getCurrentUserDetails() {
		return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static long getCurrentUserId() {
		return getCurrentUserDetails().getId();
	}

	public static List<UserRole> getCurrentUserRoles() {
		return getCurrentUserDetails().getAuthorities().stream()
				.map(authority -> UserRole.valueOf(authority.getAuthority()))
				.collect(Collectors.toList());
	}

	public static String getRedirectUrl() {
		if (getCurrentUserRoles().contains(UserRole.ROLE_ADMIN)) {
			return URL_BY_ROLE.get(UserRole.ROLE_ADMIN);
		}

		return URL_BY_ROLE.get(UserRole.ROLE_USER);
	}

	public static String getCurrentUserIpAddress() {
		Object details =  SecurityContextHolder.getContext().getAuthentication().getDetails();
		Assert.isTrue(details.getClass().isAssignableFrom(WebAuthenticationDetails.class), "details is not WebAuthenticationDetails");

		return ((WebAuthenticationDetails) details).getRemoteAddress();
	}

}
