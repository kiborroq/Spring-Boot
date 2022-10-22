package edu.school21.springboot.dto;

import edu.school21.springboot.model.User;
import edu.school21.springboot.model.type.UserRole;
import lombok.Value;

@Value
public class UserBriefOutDto {

	/** Id пользователя */
	Long id;
	/** Почта пользователя */
	String email;
	/** Является ли админом */
	Boolean isAdmin;

	public UserBriefOutDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.isAdmin = user.getRoles().contains(UserRole.ROLE_ADMIN);
	}

}
