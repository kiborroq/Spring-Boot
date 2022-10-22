package edu.school21.springboot.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Setter
@Value
public class ChatNotification {

    /** текст сообщения */
    String text;
    /** отправитель сообщения */
    Long authorId;
    /** дата сообщения */
    LocalDateTime dateTimeCreate;
    /** id фильма */
    Long filmId;

}
