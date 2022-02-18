package com.dichen.leaderBoard.model;

import jdk.jfr.DataAmount;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Serializable;

@RedisHash("USER")
public class User implements Serializable {
    @Indexed
    private String username;

    private long index;
    private int score;
    private int id;

    public User() {
    }

    @PersistenceConstructor
    public User(long index, String username, int score) {
        this.username = username;
        this.score = score;
        this.index = index;
    }
    @PersistenceConstructor
    public User( String username, int score) {
        this.username = username;
        this.score = score;
    }
    @PersistenceConstructor
    public User( String username) {
        this.username = username;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
