package edu.school21.springboot.verification;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ValidPasswordConstraintValidator.class)
public @interface ValidPassword {
    String message() default "{error.validation.signUp.password}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
