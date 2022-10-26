package edu.school21.springboot.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Value
public class SignUpInDto {

	/** Фамилия пользователя */
	@Size(message = "{error.validation.signUp.lastName}", min = 1, max = 127)
	String lastName;
	/** Имя пользователя */
	@Size(message = "{error.validation.signUp.firstName}", min = 1, max = 127)
	String firstName;
	/** Телефон пользователя */
	@Pattern(regexp = "^\\+7\\(\\d\\d\\d\\)\\d\\d\\d\\d\\d\\d\\d", message = "{error.validation.signUp.phone}")
	String phone;
	/** Почта пользователя */
	@Size(message = "{error.validation.signUp.email}", min = 6, max = 320)
	@Email(message = "{error.validation.signUp.email}")
	String email;
	/** Пароль */
	@Size(message = "{error.validation.signUp.password}", min = 5, max = 255)
	String password;

}
