package com.dichen.leaderBoard;

import com.dichen.leaderBoard.configuration.ApplicationConfig;
import com.dichen.leaderBoard.controller.UserController;
import com.dichen.leaderBoard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LeaderBoardApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
		assertThat(userService).isNotNull();
		assertThat(redisTemplate).isNotNull();
		assertThat(applicationConfig).isNotNull();
	}

}
