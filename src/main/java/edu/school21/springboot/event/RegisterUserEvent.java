package edu.school21.springboot.event;

import lombok.Value;

@Value
public class RegisterUserEvent {

	/** Id зарегистрированного пользователя */
	Long userId;

}
