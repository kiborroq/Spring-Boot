package edu.school21.springboot.dto;

import edu.school21.springboot.model.User;
import edu.school21.springboot.model.UserAvatar;
import edu.school21.springboot.model.UserSession;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class UserOutDto {

	/** Id пользователя */
	Long id;
	/** Фамилия пользователя */
	String lastName;
	/** Имя пользователя */
	String firstName;
	/** Почта пользователя */
	String email;
	/** Аватары */
	List<AvatarDto> avatars;
	/** Сессии */
	List<SessionDto> sessions;

	public UserOutDto (User user) {
		this.id = user.getId();
		this.lastName = user.getLastName();
		this.firstName = user.getFirstName();
		this.email = user.getEmail();
		this.avatars = user.getAvatars().stream().map(AvatarDto::new).collect(Collectors.toList());
		this.sessions = user.getSessions().stream().map(SessionDto::new).collect(Collectors.toList());
	}

	@Value
	public static class AvatarDto {

		/** Тип */
		String type;
		/** Размер */
		Long size;
		/** Наименование */
		String name;
		/** Дата создания */
		LocalDateTime dateTimeCreate;
		/** Ссылка на аватар */
		String url;

		public AvatarDto(UserAvatar avatar) {
			this.type = avatar.getType();
			this.size = avatar.getSize();
			this.name = avatar.getName();
			this.dateTimeCreate = avatar.getDateTimeCreate();
			this.url = String.format("/user/avatar/%s", avatar.getUuid());
		}

	}

	@Value
	public static class SessionDto {

		/** Ip адрес */
		String ip;
		/** Дата сессии */
		LocalDateTime dateTimeCreate;

		public SessionDto(UserSession session) {
			this.ip = session.getIp();
			this.dateTimeCreate = session.getDateTimeCreate();
		}

	}
}
