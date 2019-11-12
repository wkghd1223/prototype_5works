package com.example.prototype_5works;

public class User {
    public User(String name, int userImage){
        this.name = name;
        this.userImage = userImage;
    }
    private String name;
    private int userImage;

    public String getName() {
        return name;
    }

    public int getUserImage() {
        return userImage;
    }
    //user 정보
}
