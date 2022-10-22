package edu.school21.springboot.dto;

import edu.school21.springboot.model.Message;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MessageOutDto {

    /** id  фильма */
    Long filmId;
    /** Автор */
    UserBriefOutDto author;
    /** дата создания объекта сообщения */
    LocalDateTime dateTimeCreate;
    /** текст сообщения */
    String text;

    public MessageOutDto(Message message){
        filmId = message.getId();
        author = new UserBriefOutDto(message.getAuthor());
        dateTimeCreate = message.getDateTimeCreate();
        text = message.getText();
    }
}
