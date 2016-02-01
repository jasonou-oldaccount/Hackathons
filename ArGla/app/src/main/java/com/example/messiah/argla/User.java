package com.example.messiah.argla;

/**
 * Created by Messiah on 1/29/2016.
 */
public class User {
    private String userName;
    private String passWord;
    private int happy;
    private int upset;
    private int neutral;

    public User() {}

    public User(String user, String pass, int happy, int upset, int neutral) {
        this.userName = user;
        this.passWord = pass;
        this.happy = happy;
        this.upset = upset;
        this.neutral = neutral;
    }

    public String getUserName() { return userName; }

    public String getPassWord() {
        return passWord;
    }

    public int getHappy() {
        return happy;
    }

    public int getUpset() {
        return upset;
    }

    public int getNeutral() {
        return neutral;
    }
}
