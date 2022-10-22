package edu.school21.springboot.dto;

import lombok.Value;

import java.util.List;

@Value
public class FilmChatOutDto {

	/** Фильм */
	FilmOutDto film;
	/** Текущий пользователь */
	UserBriefOutDto user;
	/** First 20 messages */
	List<MessageOutDto> messages;

}
