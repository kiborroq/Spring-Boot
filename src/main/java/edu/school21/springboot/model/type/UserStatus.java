package edu.school21.springboot.model.type;

public enum UserStatus {

	/** Зарегистрированный */
	REGISTERED,
	/** Неподтвержденный */
	NOT_CONFIRMED,
	/** Активный */
	ACTIVE;

	public static final int LENGTH = 15;

}
