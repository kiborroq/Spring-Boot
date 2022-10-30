package edu.school21.springboot.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView resolveException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		ex.printStackTrace();

		String message;
		if (ex instanceof CinemaRuntimeException) {
			message = ex.getMessage();
		} else if (ex instanceof IllegalArgumentException
				|| ex instanceof MethodArgumentNotValidException
				|| ex instanceof ConstraintViolationException) {
			message = "Data input error";
		} else {
			message = "An error has occurred";
		}

		String refererUri = request.getHeader(HttpHeaders.REFERER).replaceFirst(request.getHeader(HttpHeaders.ORIGIN), "");
		RedirectView redirectView = new RedirectView(refererUri);
		redirectView.setPropagateQueryParams(false);

		Map<String, Object> data = new HashMap<>();
		data.put("error", message);

		return new ModelAndView(redirectView, data);
	}
}
