package edu.school21.springboot.dto;

import edu.school21.springboot.model.FilmSession;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FilmSessionOutDto {

	/** Id сессии */
	Long id;
	/** Зал */
	HallOutDto hall;
	/** Фильм */
	FilmOutDto film;
	/** Время показа, от */
	LocalDateTime sessionDateTimeFrom;
	/** Время показа, от */
	LocalDateTime sessionDateTimeTo;
	/** Стоимость билета */
	Integer ticketCost;

	public FilmSessionOutDto(FilmSession filmSession) {
		this.id = filmSession.getId();
		this.film = new FilmOutDto(filmSession.getFilm());
		this.hall = new HallOutDto(filmSession.getHall());
		this.sessionDateTimeFrom = filmSession.getSessionDateTimeFrom();
		this.sessionDateTimeTo = filmSession.getSessionDateTimeTo();
		this.ticketCost = filmSession.getTicketCost();
	}
}
