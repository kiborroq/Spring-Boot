package edu.school21.springboot.dto;

import lombok.Value;

@Value
public class SignUpInDto {

	/** Фамилия пользователя */
	String lastName;
	/** Имя пользователя */
	String firstName;
	/** Телефон пользователя */
	String phone;
	/** Почта пользователя */
	String email;
	/** Пароль */
	String password;

}
