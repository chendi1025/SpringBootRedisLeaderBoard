package com.dichen.leaderBoard;

import com.dichen.leaderBoard.model.User;
import com.dichen.leaderBoard.userRepository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackageClasses = UserRepository.class)
public class LeaderBoardApplication {
//
//	@Bean
//	JedisConnectionFactory jedisConnectionFactory(){
//		return new JedisConnectionFactory();
//	}

//	@Bean
//	RedisTemplate<?, ?> redisTemplate(){
//		RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
//		return redisTemplate;
//	}

	public static void main(String[] args) {
//		ApplicationContext ctx = SpringApplication.run(SpringBootNoBeanApplication.class, args);
//		ctx.getBean("lion", Lion.class);
//
		SpringApplication.run(LeaderBoardApplication.class, args);
	}

}
