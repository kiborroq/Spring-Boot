package edu.school21.springboot.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

@ControllerAdvice(basePackages = "edu.school21.springboot")
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ModelAndView resolveException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		ex.printStackTrace();

		String message;
		if (ex instanceof CinemaRuntimeException) {
			message = ex.getMessage();
		} else if (ex instanceof IllegalArgumentException
				|| ex instanceof MethodArgumentNotValidException
				|| ex instanceof ConstraintViolationException) {
			message = messageSource.getMessage("error.common.input-data", null, LocaleContextHolder.getLocale());
		} else {
			message = messageSource.getMessage("error.common.server", null, LocaleContextHolder.getLocale());
		}

		String refererUri = request.getHeader(HttpHeaders.REFERER).replaceFirst(request.getHeader(HttpHeaders.ORIGIN), "");
		RedirectView redirectView = new RedirectView(refererUri);
		redirectView.setPropagateQueryParams(false);

		Map<String, Object> data = new HashMap<>();
		data.put("error", message);

		return new ModelAndView(redirectView, data);
	}
}
