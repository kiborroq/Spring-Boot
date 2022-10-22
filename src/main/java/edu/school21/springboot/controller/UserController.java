package edu.school21.springboot.controller;

import edu.school21.springboot.dto.FilmSessionOutDto;
import edu.school21.springboot.dto.MessageInDto;
import edu.school21.springboot.dto.MessageOutDto;
import edu.school21.springboot.service.UserService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/** Controller for user interaction with Cinema */
@Controller
@RequestMapping
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("profile")
	public String getProfile(@ModelAttribute("model") ModelMap model) {
		model.addAttribute("user", userService.getProfile());
		return "profile";
	}

	@GetMapping("sessions")
	public String getSessions(@ModelAttribute("model") ModelMap model) {
		model.addAttribute("sessions", userService.searchSessions(null));
		return "sessions-search";
	}

	@GetMapping("sessions/{sessionId}")
	public String getSession(@PathVariable long sessionId, @ModelAttribute("model") ModelMap model) {
		model.addAttribute("session", userService.getSession(sessionId));
		return "session-info";
	}

	@ResponseBody
	@GetMapping("films/{filmId}/messages")
	public List<MessageOutDto> getMessages(@PathVariable("filmId") long filmId,
										   @RequestParam("offset") int offset,
										   @RequestParam("limit") int limit) {
		return userService.getMessages(filmId, offset, limit);
	}

	@GetMapping("films/{filmId}/chat")
	public String getFilmChat(@PathVariable long filmId, @ModelAttribute("model") ModelMap model){
		model.addAttribute("chat", userService.getFilmChat(filmId));
		return "film-chat";
	}

	@MessageMapping("film/{filmId}/chat/messages/send")
	public void sendMessage(@DestinationVariable("filmId") long filmId, @Payload MessageInDto dto) {
		userService.sendMessage(dto, filmId);
	}

	@PostMapping("user/avatar")
	public String uploadAvatar(@RequestParam("image") MultipartFile image) {
		userService.uploadAvatar(image);
		return "redirect:/profile";
	}

	@ResponseBody
	@GetMapping("user/avatar/{avatarUuid}")
	public void getAvatar(@PathVariable("avatarUuid") UUID avatarUuid, HttpServletResponse response) {
		userService.getAvatar(avatarUuid, response);
	}

	@ResponseBody
	@GetMapping("sessions/search")
	public List<FilmSessionOutDto> searchSessions(@RequestParam(value = "filmName", required = false) @Nullable String filmTitle,
												  @ModelAttribute("model") ModelMap model) {
		return userService.searchSessions(filmTitle);
	}

	@ResponseBody
	@GetMapping("film/{filmId}/poster")
	public void getFilmPoster(@PathVariable long filmId, HttpServletResponse response) {
		userService.getFilmPoster(filmId, response);
	}

}
