package edu.school21.springboot.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Value
public class FilmSessionInDto {

	/** Зал */
	Long hallId;
	/** Фильм */
	Long filmId;
	/** Время показа, от */
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	LocalDateTime sessionDateTime;
	/** Стоимость билета */
	Integer ticketCost;
}
