package com.dichen.leaderBoard.service;

import com.dichen.leaderBoard.model.User;


import java.util.List;
import java.util.Set;

public interface UserService {
    User save(String userName, int score);

    List<User> findAll(int pagaNumber, int pageSize);

    List<User> find(String username, int pageSize) throws Exception;

}
