package edu.school21.springboot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "film_session")
public class FilmSession extends AbstractModel {

	/** Зал */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id", nullable = false, updatable = false)
	private Hall hall;

	/** Фильм */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "film_id", nullable = false, updatable = false)
	private Film film;

	/** Время показа, от */
	@Column(nullable = false)
	private LocalDateTime sessionDateTimeFrom;

	/** Время показа, до */
	@Column(nullable = false)
	private LocalDateTime sessionDateTimeTo;

	/** Стоимость билета */
	@Column(nullable = false)
	private Integer ticketCost;
}
