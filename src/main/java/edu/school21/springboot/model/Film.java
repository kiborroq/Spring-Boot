package edu.school21.springboot.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "Film")
public class Film extends AbstractModel {

	public final static int TITLE_LENGTH = 100;
	public final static int DESCRIPTION_LENGTH = 1000;

	/** Название */
	@Column(nullable = false, length = TITLE_LENGTH, unique = true)
	private String title;

	/** Год выпуска */
	@Column(nullable = false)
	private Integer yearOfRelease;

	/** Ограничение по возрасту */
	@Column(nullable = false)
	private Byte ageRestrictions;

	/** Описание */
	@Nullable
	@Column(length = DESCRIPTION_LENGTH)
	private String description;

	/** Длительность в минутах */
	@Column(nullable = false)
	private Integer duration;

	/** Постер фильма */
	@Nullable
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "poster_file_id", unique = true)
	private FilmPoster posterFile;

}
