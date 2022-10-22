package edu.school21.springboot.dto;

import lombok.Value;

@Value
public class MessageInDto {

    /** текст сообщения */
    String text;
    /** отправитель сообщения */
    Long authorId;

}
