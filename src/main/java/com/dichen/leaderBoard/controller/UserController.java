package com.dichen.leaderBoard.controller;

import com.dichen.leaderBoard.model.User;
import com.dichen.leaderBoard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    public static final int PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 50;

    @Autowired
    private UserService userService;


    @PostMapping("/leaderBoard/{username}/{score}")
    public ResponseEntity add(@PathVariable("username") final String username,
                              @PathVariable("score") final int score) {
        if(username == null || username.isEmpty()){
            throw new IllegalArgumentException("username cannot be null");
        }
        return ResponseEntity.ok(userService.save(username, score));
    }


    @GetMapping(value ="/leaderBoard/{username}/{pageSize}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object get(@PathVariable("username") final String username,
                      @PathVariable("pageSize") int pageSize) {
        if(username == null || username.isEmpty()){
            throw new IllegalArgumentException("username cannot be null");
        }
        pageSize = pageSize == 0 ? PAGE_SIZE : pageSize;
        List<User> users = null;
        try {
            users = userService.find(username, pageSize);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        if(users == null || users.isEmpty()){
            String s = String.format("User  %s not found", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s);
        }
        return ResponseEntity.ok(users);
    }


    @GetMapping("/leaderBoard/all/{pageNumber}/{pageSize}")
    public ResponseEntity getAll(@PathVariable("pageNumber") int pageNumber,
                                 @PathVariable("pageSize") int pageSize) {
        pageNumber = pageNumber == 0 ? PAGE_NUMBER : pageNumber;
        pageSize = pageSize == 0 ? PAGE_SIZE : pageSize;
        Map<String, Object> map = new LinkedHashMap();
        map.put("leaderBoardEntries", userService.findAll(pageNumber, pageSize));
        map.put("page", pageNumber);
        map.put("count", pageSize);
        return ResponseEntity.ok(map);
    }


}
