package edu.school21.springboot.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView resolveException(Exception ex) {
		Integer statusCode;
		if (ex instanceof CinemaRuntimeException) {
			statusCode = ((CinemaRuntimeException) ex).getStatusCode();
		} else if (ex instanceof IllegalArgumentException
				|| ex instanceof MethodArgumentNotValidException
				|| ex instanceof ConstraintViolationException) {
			statusCode = HttpStatus.BAD_REQUEST.value();
		} else {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		}

		Map<String, Object> data = new HashMap<>();
		data.put("status", statusCode);
		data.put("error", ex.getMessage());

		ModelAndView view = new ModelAndView("error", data);
		view.setStatus(HttpStatus.valueOf(statusCode));

		return view;
	}
}
