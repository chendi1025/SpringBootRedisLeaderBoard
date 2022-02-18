package com.dichen.leaderBoard;

import com.dichen.leaderBoard.controller.UserController;
import com.dichen.leaderBoard.model.User;
import com.dichen.leaderBoard.service.UserService;
import com.dichen.leaderBoard.service.UserServiceImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
//@SpringBootTest(classes=UserService.class)
public class ControllerTest {
//    @Autowired
//    @MockBean
//    private MockMvc mvc;

    @Autowired
    @MockBean
    private UserService userService;

    @Autowired
    @MockBean
    private RedisTemplate redisTemplate;

    private String URL = "http://localhost:8080/api/";
    private int HAPPY_ADDED = 200;

    @Test
    public void addUserTest() {
        String name = "testUser";
        int score = 100;

        HttpUriRequest request = new HttpGet( URL + name +"/" + score );

        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute( request );
        } catch (IOException e) {
            fail();
        }

        assertEquals(httpResponse.getStatusLine().getStatusCode(),HAPPY_ADDED);
    }

}
