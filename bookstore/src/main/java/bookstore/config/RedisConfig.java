package bookstore.config;

import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, LinkedList<HashMap<String, Long>>> shoppingCartRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, LinkedList<HashMap<String, Long>>> redisTemplate = new RedisTemplate<String, LinkedList<HashMap<String, Long>>>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
	
	@Bean
	public RedisTemplate<String, HashMap<String, Long>> shoppingCartTempRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, HashMap<String, Long>> redisTemplate = new RedisTemplate<String, HashMap<String, Long>>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
}
