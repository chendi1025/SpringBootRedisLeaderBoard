package com.dichen.leaderBoard.userRepository;

import com.dichen.leaderBoard.model.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl {

    private RedisTemplate<String, User> redisTemplate;
    private HashOperations hashOperations;
//
//    public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        hashOperations = redisTemplate.opsForHash();
//    }
//
//    @Override
//    public Object save(User user) {
//        hashOperations.put("USER", user.getId(), user);
//        return null;
//    }
//
//    @Override
//    public Map<String,User> findAll() {
//        return hashOperations.entries("USER");
//    }
//
//    @Override
//    public Optional<User> findById(String id) {
//        return (Optional<User>) hashOperations.get("USER", id);
//    }
//
//    @Override
//    public void updateUser(User user) {
//        save(user);
//    }
//
//    @Override
//    public void deleteUser(User user) {
//        hashOperations.delete("USER", user.getId());
//    }
}
