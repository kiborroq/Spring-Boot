package edu.school21.springboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_session")
public class UserSession extends AbstractModel {

	public static final int IP_LENGTH = 50;

	/** Ip адрес */
	@Column(length = IP_LENGTH, nullable = false, updatable = false)
	private String ip;

	/** Дата сессии */
	@Column(name = "datetime_create", nullable = false, updatable = false)
	private LocalDateTime dateTimeCreate = LocalDateTime.now();

}
