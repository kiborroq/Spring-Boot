package edu.school21.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
				.nullDestMatcher().authenticated()
				.simpDestMatchers("/app/**").authenticated()
				.simpSubscribeDestMatchers("/films/**").authenticated()
				.anyMessage().denyAll();
	}

}
