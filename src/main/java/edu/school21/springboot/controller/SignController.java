package edu.school21.springboot.controller;

import edu.school21.springboot.dto.SignUpInDto;
import edu.school21.springboot.security.SecurityUtils;
import edu.school21.springboot.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.UUID;

/** Controller for sign in and sign up */
@Controller
public class SignController {

	@Autowired
	private SignService signService;

	@GetMapping("signIn")
	public String signIn() {
		if (SecurityUtils.isAuthenticated()) {
			return "redirect:" + SecurityUtils.getRedirectUrl();
		}

		return "signIn";
	}

	@GetMapping("signUp")
	public String signUp(Model model) {
		if (SecurityUtils.isAuthenticated()) {
			return "redirect:" + SecurityUtils.getRedirectUrl();
		}

		model.addAttribute("user", SignUpInDto.builder().build());
		return "signUp";
	}

	@PostMapping("signUp")
	public String signUp(@Valid @ModelAttribute("user") SignUpInDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signUp";
		}

		signService.signUp(dto);
		return "redirect:/signIn?confirmEmail";
	}

	@GetMapping("/confirm/{token}")
	public String confirm(@PathVariable("token") UUID token) {
		signService.confirm(token);
		return "/signIn";
	}

}
