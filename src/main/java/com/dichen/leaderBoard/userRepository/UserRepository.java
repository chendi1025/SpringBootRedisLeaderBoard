package com.dichen.leaderBoard.userRepository;

import com.dichen.leaderBoard.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
//
//    Object save(User user);
//
//    List<User> findAll();


//    List<User> findAllById(Iterable<String> ids);

//    void deleteAll();

//    Optional<User> findById(String id);
//
//    void updateUser(User user);
//
//    void deleteUser(User user);
}
