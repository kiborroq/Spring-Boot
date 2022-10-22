package edu.school21.springboot.service;

import edu.school21.springboot.dto.FilmInDto;
import edu.school21.springboot.dto.FilmOutDto;
import edu.school21.springboot.dto.FilmSessionInDto;
import edu.school21.springboot.dto.FilmSessionOutDto;
import edu.school21.springboot.dto.HallInDto;
import edu.school21.springboot.dto.HallOutDto;
import edu.school21.springboot.exception.CinemaRuntimeException;
import edu.school21.springboot.model.FilmPoster;
import edu.school21.springboot.model.Film;
import edu.school21.springboot.model.FilmSession;
import edu.school21.springboot.model.Hall;
import edu.school21.springboot.repository.FilmRepository;
import edu.school21.springboot.repository.FilmSessionRepository;
import edu.school21.springboot.repository.HallRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminService {

	private final static Integer ADDITIONAL_DURATION = 60;

	@Value("${edu.school21.spring-boot.poster-path}")
	private String postersPath;

	@Autowired
	private HallRepository hallRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private FilmSessionRepository filmSessionRepository;

	@Transactional(readOnly = true)
	public List<HallOutDto> getHalls() {
		return hallRepository.findAll().stream()
				.map(HallOutDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public void createHall(HallInDto dto) {
		Hall hall = new Hall();
		hall.setSeatsCount(dto.getSeatsCount());
		hallRepository.save(hall);

		log.debug("Hall was created: {}", dto);
	}

	@Transactional(readOnly = true)
	public List<FilmOutDto> getFilms() {
		return filmRepository.findAll().stream()
				.map(FilmOutDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public void createFilm(FilmInDto dto){
		Film film = new Film();
		film.setTitle(dto.getTitle());
		film.setYearOfRelease(dto.getYearOfRelease());
		film.setAgeRestrictions(dto.getAgeRestrictions());
		film.setDescription(dto.getDescription());
		film.setDuration(dto.getDuration());
		filmRepository.save(film);

		log.debug("Film was created: {}", dto);
	}

	@Transactional
	public void uploadFilmPoster(long filmId, MultipartFile image) {
		Film film = filmRepository.findById(filmId)
				.orElseThrow(() -> new CinemaRuntimeException("Film not found", HttpStatus.NOT_FOUND.value()));

		try (FileOutputStream fos = new FileOutputStream(postersPath + filmId)) {
			IOUtils.copy(image.getInputStream(), fos);

			FilmPoster filmPoster = film.getPosterFile();
			if (filmPoster == null) {
				filmPoster = new FilmPoster();
				film.setPosterFile(filmPoster);
			}

			filmPoster.setSize(image.getSize());
			filmPoster.setType(image.getContentType());
			filmPoster.setName(image.getOriginalFilename());
			filmRepository.save(film);

		} catch (IOException e) {
			throw new CinemaRuntimeException("Error during image upload", HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
		}

		log.debug("For film {} poster was added: {}", filmId, image.getOriginalFilename());
	}

	@Transactional
	public void createSession(FilmSessionInDto dto) {
		Film film = filmRepository.findById(dto.getFilmId())
				.orElseThrow(() -> new CinemaRuntimeException("Film not found", HttpStatus.NOT_FOUND.value()));

		Hall hall = hallRepository.findById(dto.getHallId())
				.orElseThrow(() -> new CinemaRuntimeException("Hall not found", HttpStatus.NOT_FOUND.value()));

		LocalDateTime sessionDateTimeFrom = dto.getSessionDateTime();
		LocalDateTime sessionDateTimTo = sessionDateTimeFrom.plusMinutes(film.getDuration() + ADDITIONAL_DURATION);
		assertSessionTime(sessionDateTimeFrom, sessionDateTimTo, hall);

		FilmSession filmSession = new FilmSession();
		filmSession.setFilm(film);
		filmSession.setHall(hall);
		filmSession.setTicketCost(dto.getTicketCost());
		filmSession.setSessionDateTimeFrom(sessionDateTimeFrom);
		filmSession.setSessionDateTimeTo(sessionDateTimTo);
		filmSessionRepository.save(filmSession);

		log.debug("Session was created: {}", dto);
	}

	@Transactional(readOnly = true)
	public List<FilmSessionOutDto> getSessions() {
		return filmSessionRepository.findAll().stream()
				.map(FilmSessionOutDto::new)
				.collect(Collectors.toList());
	}


	private void assertSessionTime(LocalDateTime sessionDateTimeFrom, LocalDateTime sessionDateTimeTo, Hall hall) {
		List<FilmSession> filmSessions = filmSessionRepository.findAllByHall(hall);

		for (FilmSession session : filmSessions) {
			LocalDateTime from = session.getSessionDateTimeFrom();
			LocalDateTime to = session.getSessionDateTimeTo();

			if ((sessionDateTimeFrom.isBefore(from) && sessionDateTimeTo.isAfter(from))
					|| (sessionDateTimeFrom.isBefore(to) && sessionDateTimeFrom.isAfter(to))) {
				throw new CinemaRuntimeException(String.format("Hall already busy by '%s'",
						session.getFilm().getTitle()),
						HttpStatus.BAD_REQUEST.value());
			}
		}
	}
}
