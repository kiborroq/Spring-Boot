package edu.school21.springboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractFile extends AbstractModel {

	public static final int TYPE_LENGTH = 255;
	public static final int NAME_LENGTH = 255;

	/** Тип */
	@Column(nullable = false, length = TYPE_LENGTH)
	private String type;

	/** Размер */
	@Column(nullable = false)
	private Long size;

	/** Наименование */
	@Column(nullable = false, length = NAME_LENGTH)
	private String name;

	/** Дата создания */
	@Column(name = "datetime_create", nullable = false, updatable = false)
	private LocalDateTime dateTimeCreate = LocalDateTime.now();

}
