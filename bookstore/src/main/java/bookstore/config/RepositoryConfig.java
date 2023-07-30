package bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bookstore.handler.user.UserEventHandler;

@Configuration
public class RepositoryConfig {

	@Bean
	public UserEventHandler userEventHandler() {
		return new UserEventHandler();
	}
}
