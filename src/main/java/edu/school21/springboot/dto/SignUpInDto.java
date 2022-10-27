package edu.school21.springboot.dto;

import edu.school21.springboot.verification.ValidPassword;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.ConstraintValidator;

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
	@Pattern(regexp = "\\+\\d{1,3}\\(\\d{3}\\)\\d{7}$", message = "{error.validation.signUp.phone}")
	String phone;
	/** Почта пользователя */
	@Size(message = "{error.validation.signUp.email}", min = 6, max = 320)
	@Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{error.validation.signUp.email}")
	String email;
	/** Пароль */
	@Size(message = "{error.validation.signUp.password}", min = 5, max = 255)
	@ValidPassword(message = "{error.validation.signUp.password}")
	String password;

}
