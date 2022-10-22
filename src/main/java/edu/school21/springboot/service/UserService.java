package edu.school21.springboot.service;

import edu.school21.springboot.dto.FilmChatOutDto;
import edu.school21.springboot.dto.FilmOutDto;
import edu.school21.springboot.dto.FilmSessionOutDto;
import edu.school21.springboot.dto.MessageInDto;
import edu.school21.springboot.dto.MessageOutDto;
import edu.school21.springboot.dto.UserBriefOutDto;
import edu.school21.springboot.dto.UserOutDto;
import edu.school21.springboot.exception.CinemaRuntimeException;
import edu.school21.springboot.model.Film;
import edu.school21.springboot.model.FilmPoster;
import edu.school21.springboot.model.FilmSession;
import edu.school21.springboot.model.Message;
import edu.school21.springboot.model.User;
import edu.school21.springboot.model.UserAvatar;
import edu.school21.springboot.repository.FilmRepository;
import edu.school21.springboot.repository.FilmSessionRepository;
import edu.school21.springboot.repository.MessageRepository;
import edu.school21.springboot.repository.UserRepository;
import edu.school21.springboot.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

	private final static FilmPoster DEFAULT_POSTER;
	private final static String DEFAULT_POSTER_PATH;

	static {
		URL postersDir = AdminService.class.getClassLoader().getResource("files/posters/");
		Assert.notNull(postersDir, "postersDir is null");

		DEFAULT_POSTER_PATH = postersDir.getPath();

		DEFAULT_POSTER = new FilmPoster();
		DEFAULT_POSTER.setName("default-poster.jpg");
		DEFAULT_POSTER.setType(MimeTypeUtils.IMAGE_JPEG_VALUE);
	}

	@Value("${edu.school21.spring-boot.avatar-path}")
	private String avatarsPath;
	@Value("${edu.school21.spring-boot.poster-path}")
	private String postersPath;

	@Autowired
	private FilmSessionRepository filmSessionRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;


	@Transactional(readOnly = true)
	public UserOutDto getProfile() {
		return userRepository.findById(SecurityUtils.getCurrentUserId())
				.map(UserOutDto::new)
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));
	}

	@Transactional(readOnly = true)
	public FilmSessionOutDto getSession(long sessionId) {
		FilmSession session = filmSessionRepository.findById(sessionId)
				.orElseThrow(() -> new CinemaRuntimeException("Session not found", HttpStatus.NOT_FOUND.value()));

		return new FilmSessionOutDto(session);
	}

	@Transactional(readOnly = true)
	public FilmChatOutDto getFilmChat(long filmId) {
		Film film = filmRepository.findById(filmId)
				.orElseThrow(() -> new CinemaRuntimeException("Film not found", HttpStatus.NOT_FOUND.value()));

		User user = userRepository.findById(SecurityUtils.getCurrentUserId())
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));

		List<MessageOutDto> messages = messageRepository.findAllByFilm(film.getId(), 0, 20).stream()
				.map(MessageOutDto::new)
				.collect(Collectors.toList());

		Collections.reverse(messages);

		return new FilmChatOutDto(new FilmOutDto(film), new UserBriefOutDto(user), messages);
	}

	@Transactional
	public void sendMessage(MessageInDto dto, long filmId) {
		User user = userRepository.findById(dto.getAuthorId())
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));

		Film film = filmRepository.findById(filmId)
				.orElseThrow(() -> new CinemaRuntimeException("Film not found", HttpStatus.NOT_FOUND.value()));

		Message message = new Message();
		message.setText(dto.getText());
		message.setAuthor(user);
		message.setFilm(film);
		messageRepository.save(message);

		simpMessagingTemplate.convertAndSendToUser(String.valueOf(filmId), "/chat/messages", new MessageOutDto(message));
	}

	@Transactional(readOnly = true)
	public List<MessageOutDto> getMessages(long filmId, int offset, int limit) {
		Film film = filmRepository.findById(filmId)
				.orElseThrow(() -> new CinemaRuntimeException("Film not found", HttpStatus.NOT_FOUND.value()));

		return messageRepository.findAllByFilm(film.getId(), offset, limit).stream()
				.map(MessageOutDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public void uploadAvatar(MultipartFile image) {
		User user = userRepository.findById(SecurityUtils.getCurrentUserId())
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));

		UserAvatar avatar = new UserAvatar();
		avatar.setSize(image.getSize());
		avatar.setType(image.getContentType());
		avatar.setName(image.getOriginalFilename());
		user.getAvatars().add(avatar);

		try (FileOutputStream fos = new FileOutputStream(avatarsPath + avatar.getUuid().toString())) {
			IOUtils.copy(image.getInputStream(), fos);
		} catch (IOException e) {
			throw new CinemaRuntimeException("Error during image upload", HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
		}

		log.debug("For user {} avatar was added: {}", user.getEmail(), image.getOriginalFilename());
	}

	@Transactional(readOnly = true)
	public void getAvatar(UUID avatarUuid, HttpServletResponse response) {
		User user = userRepository.findById(SecurityUtils.getCurrentUserId())
				.orElseThrow(() -> new CinemaRuntimeException("User not found", HttpStatus.NOT_FOUND.value()));

		UserAvatar avatar = user.getAvatars().stream()
				.filter(a -> a.getUuid().equals(avatarUuid))
				.findFirst()
				.orElseThrow(() -> new CinemaRuntimeException("User avatar not found", HttpStatus.NOT_FOUND.value()));

		try (FileInputStream fis = new FileInputStream(avatarsPath + avatarUuid)) {
			Assert.notNull(fis, "fis is null");

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(avatar.getType());
			response.addHeader("Content-Disposition", String.format("filename=\"%s\"", avatar.getName()));

			IOUtils.copy(fis, response.getOutputStream());
		} catch (IOException e) {
			throw new CinemaRuntimeException("Error during user avatar reading", HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
		}
	}

	@Transactional(readOnly = true)
	public List<FilmSessionOutDto> searchSessions(@Nullable String filmTitle) {
		return Optional.ofNullable(filmTitle)
				.map(String::toLowerCase)
				.map(filmSessionRepository::findAllByFilmTitle)
				.orElseGet(() -> filmSessionRepository.findAllByFilmTitle(""))
				.stream()
				.map(FilmSessionOutDto::new)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public void getFilmPoster(Long filmId, HttpServletResponse response) {
		Film film = filmRepository.findById(filmId)
				.orElseThrow(() -> new CinemaRuntimeException("Film not found", HttpStatus.NOT_FOUND.value()));

		FilmPoster filmPoster;
		String filePath;
		if (film.getPosterFile() != null) {
			filmPoster = film.getPosterFile();
			filePath = postersPath + film.getId();
		} else {
			filmPoster = DEFAULT_POSTER;
			filePath = DEFAULT_POSTER_PATH + DEFAULT_POSTER.getName();
		}

		try (FileInputStream fis = new FileInputStream(filePath)) {
			Assert.notNull(fis, "fis is null");

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(filmPoster.getType());
			response.addHeader("Content-Disposition", String.format("filename=\"%s\"", filmPoster.getName()));

			IOUtils.copy(fis, response.getOutputStream());
		}
		catch (FileNotFoundException e) {
			throw new CinemaRuntimeException("Image not found", HttpStatus.NOT_FOUND.value(), e);
		} catch (IOException e) {
			throw new CinemaRuntimeException("Error during image reading", HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
		}
	}

}
