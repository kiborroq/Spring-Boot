package edu.school21.springboot.controller;

import edu.school21.springboot.dto.SignUpInDto;
import edu.school21.springboot.security.SecurityUtils;
import edu.school21.springboot.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

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
	public String signUp(@ModelAttribute("model") ModelMap model) {
		System.out.println(SecurityUtils.isAuthenticated());

		if (SecurityUtils.isAuthenticated()) {
			return "redirect:" + SecurityUtils.getRedirectUrl();
		}

		model.addAttribute("errors", Collections.EMPTY_MAP);
		model.addAttribute("fields", Collections.EMPTY_MAP);
		return "signUp";
	}

	@PostMapping("signUp")
	public String signUp(SignUpInDto dto, @ModelAttribute("model") ModelMap model) {
		signService.signUp(dto);
		return "redirect:/signIn";
	}

}
