package com.dichen.leaderBoard;

import com.dichen.leaderBoard.controller.UserController;
import com.dichen.leaderBoard.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ControllerIntegrationTest {
//    @Autowired
//    @MockBean
//    private MockMvc mvc;


    private static final String SCORE = "score";
    private static final String USER = "testUser";
    private static final String INDEX = "index";
    private static final String PAGE = "page";
    private static final String COUNT = "count";


    @Autowired
    @MockBean
    private UserServiceImpl userService;

    @Autowired
    @MockBean
    private RedisTemplate redisTemplate;


    @BeforeClass
    private void init() {
        addUsers();
    }

    @AfterClass
    private void tearDown() {
        cleanUp();
    }

    private static final String  URL = "http://localhost:8080/api/leaderBoard/";
    private static final int HAPPY = 200;
    private static final int HAPPY_CREATED = 201;
    private static final int SAD_BAD_REQUEST = 400;
    private static final int SAD_NOT_FOUND = 404;

    @Test
    public void addUser_pos() {
        String name = USER;
        int score = 100;
        HttpUriRequest request = new HttpPost(URL + name + "/" + score);
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(request);
            assertEquals(httpResponse.getStatusLine().getStatusCode(), HAPPY_CREATED);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addUser_missingName_neg() {
        String name = "";
        int score = 100;
        HttpUriRequest request = new HttpPost(URL + name + "/" + score);
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(request);
            assertEquals(httpResponse.getStatusLine().getStatusCode(), SAD_BAD_REQUEST);
            assertTrue(httpResponse.getStatusLine().getReasonPhrase().contains("username cannot be null"));
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void getAll_pos() {
        int pageNumber = 1;
        int pageSize = 20;
        HttpUriRequest request = new HttpGet(URL + "/all" + "/" + pageNumber + "/" + pageSize);
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(request);
            assertEquals(httpResponse.getStatusLine().getStatusCode(), HAPPY);
            String resp = getResponseString(httpResponse);
            assertTrue(resp != null && !resp.isEmpty());
            assertTrue(resp.contains("testUser1"));
            Map<String, Object> map = getResponseAsMap(httpResponse);
            assertTrue(map.get(PAGE) != null && (int) map.get(PAGE) == pageNumber);
            assertTrue(map.get(COUNT) != null && (int) map.get(COUNT) >= 1);
            assertNotNull(map.get("leaderBoardEntries"));
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void get_pos() {
        String username = USER;
        int pageSize = 10;
        HttpUriRequest request = new HttpGet(URL + username + "/" + pageSize);
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(request);
            assertEquals(httpResponse.getStatusLine().getStatusCode(), HAPPY);
            String resp = getResponseString(httpResponse);
            assertTrue(resp != null && !resp.isEmpty());
            assertTrue(resp.contains(USER + 1));
            assertTrue(resp.contains(USER + 2));
            assertTrue(resp.contains(SCORE));
            assertTrue(resp.contains(INDEX));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void get_neg() {
        String username = USER;
        int pageSize = 10;
        HttpUriRequest request = new HttpGet(URL + username+ "100" + "/" + pageSize);
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(request);
            assertEquals(httpResponse.getStatusLine().getStatusCode(), SAD_NOT_FOUND);
            String resp = getResponseString(httpResponse);
            assertTrue(resp.contains("not found"));
            assertTrue(resp != null && !resp.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }
    
    private String getResponseString(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        return EntityUtils.toString(entity, Charset.defaultCharset());
    }

    private Map<String, Object> getResponseAsMap(HttpResponse httpResponse) throws IOException {
        return new ObjectMapper().readValue(getResponseString(httpResponse), Map.class);
    }


    private void cleanUp() {
        userService.deleteAll();
    }


    private void addUsers() {
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            userService.save("testUser1" + i, random.nextInt(2000));
        }
    }
}
