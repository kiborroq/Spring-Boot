package edu.school21.springboot.manager;

public interface MailManager {

	void sendMessage(String to, String subject, String text);

}
