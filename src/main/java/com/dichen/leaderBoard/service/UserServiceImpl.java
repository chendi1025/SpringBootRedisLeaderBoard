package com.dichen.leaderBoard.service;

import com.dichen.leaderBoard.model.User;
import com.dichen.leaderBoard.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;

    private Set cachedAllUsers;
    private static final String SCORE = "score";
    private static final String USERNAME = "username";
    private static final String USERS = "users";

    public User save(String userName, int score) {
        redisTemplate.opsForValue().set(userName, new User(userName, score));
        redisTemplate.opsForZSet().add(USERS, new User(userName, score), score);
        return new User(userName, score);
    }

    private void cacheAllUsers(Set users) {
        cachedAllUsers = users;
    }

    private Set getCachAllUsers() {
        if(cachedAllUsers == null ){
          cachedAllUsers = getAllUsersFromRedis();
        }
        return cachedAllUsers;
    }

    public List<User> findAll(int pageNumber, int pageSize) {
        Set users;
        if (pageNumber != 1) {
            users = getCachAllUsers();
        } else {
            users = getAllUsersFromRedis();
            cacheAllUsers(users);
        }

        List<User> usersList = new ArrayList<>();
        if (users != null) {
            int indexStart = pageNumber * pageSize - pageSize;
            int indexEnd = pageNumber * pageSize - 1;
            loadUsers(usersList, users, indexStart, indexEnd);
        }
        return usersList;
    }

    private Set getAllUsersFromRedis() {
        return redisTemplate.opsForZSet().reverseRangeByScore(USERS, 0, Integer.MAX_VALUE);
    }

    public List<User> find(String username, int pageSize) throws Exception {
        try {
            Object res = redisTemplate.opsForValue().get(username);
            LinkedHashMap u = (LinkedHashMap) res;
            if (u == null) {
                String msg = String.format("user %s is not found", username);
                throw new Exception(msg);
            }
            Long index = redisTemplate.opsForZSet().reverseRank(USERS, new User(username, ((Integer) u.get(SCORE)).intValue()));
            if (index == null) {
                String msg = String.format("user %s is not found", username);
                throw new Exception(msg);
            }
            int indexEnd = 0, indexStart = 0;
            List<User> usersList = new ArrayList<>();

            Set users = getAllUsersFromRedis();
            if (users != null) {
                int start = 0;
                if (pageSize % 2 == 1) {
                    start = (int) (index - pageSize / 2);
                } else {
                    start = (int) (index - pageSize / 2 + 1);
                }

                if (start < 0) {
                    indexEnd += Math.abs(start);
                }
                indexStart = Math.max(start, 0);

                indexEnd += (int) (index + pageSize / 2) >= users.size() ? users.size() - 1 : (int) (index + pageSize / 2);


                loadUsers(usersList, users, indexStart, indexEnd);
            }
            return usersList;
        } catch (Exception e) {
            throw e;
        }
    }

    private void loadUsers(List<User> usersList, Set users, int indexStart, int indexEnd) {
        int index = 0;


        for (Object user : users) {
            if (index > indexEnd) {
                break;
            }
            if (index >= indexStart) {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) user;
                User u = new User();
                u.setScore((Integer) map.get(SCORE));
                u.setIndex(index);
                u.setUsername((String) map.get(USERNAME));
                usersList.add(u);
            }
            index++;
        }
    }

    public void deleteAll() {
        redisTemplate.opsForZSet().removeRangeByScore(USERS, Double.MAX_VALUE, Double.MAX_VALUE);
    }

}
