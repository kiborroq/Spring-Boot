package edu.school21.springboot.model;

import edu.school21.springboot.model.type.UserRole;
import edu.school21.springboot.model.type.UserStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.jetbrains.annotations.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_account")
public class User extends AbstractModel {

	public static final int NAME_LENGTH = 127;
	public static final int EMAIL_LENGTH = 320;
	public static final int PHONE_LENGTH = 15;

	/** Фамилия пользователя */
	@Column(nullable = false, length = NAME_LENGTH)
	private String lastName;

	/** Имя пользователя */
	@Column(nullable = false, length = NAME_LENGTH)
	private String firstName;

	/** Почта пользователя */
	@Column(nullable = false, length = EMAIL_LENGTH)
	private String email;

	/** Пароль пользователя */
	@Column(nullable = false)
	private String password;

	/** Номер телефона пользователя */
	@Column(nullable = false, length = PHONE_LENGTH)
	private String phone;

	/** Статус пользователя */
	@Column(nullable = false, length = UserStatus.LENGTH)
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	/** Токен для подтверждения. Для администраторов null */
	@Nullable
	private UUID confirmToken;

	/** Аватары */
	@OrderBy("dateTimeCreate DESC")
	@BatchSize(size = BATCH_SIZE)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private List<UserAvatar> avatars = new ArrayList<>();

	/** Сессии */
	@OrderBy("dateTimeCreate DESC")
	@BatchSize(size = BATCH_SIZE)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private List<UserSession> sessions = new ArrayList<>();

	/** Роли пользователя */
	@BatchSize(size = BATCH_SIZE)
	@Column(name = "role", nullable = false, length = UserRole.LENGTH)
	@Enumerated(EnumType.STRING)
	@ElementCollection
	@CollectionTable(name = "user_role",
			joinColumns = @JoinColumn(name = "user_id", nullable = false),
			indexes = @Index(columnList = "user_id"))
	private Set<UserRole> roles = new HashSet<>();

}
