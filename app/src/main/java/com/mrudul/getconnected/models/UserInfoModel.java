package com.mrudul.getconnected.models;

public class UserInfoModel {

    private String userId,profilePic,username,email,password,lastMessage;


    // profile constructor
    public UserInfoModel(String userId, String profilePic, String username, String email, String password, String lastMessage) {
        this.userId = userId;
        this.profilePic = profilePic;
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastMessage = lastMessage;
    }

    // empty constructor for only retrieve values
    public UserInfoModel(){}


    // register page constructor
    public UserInfoModel(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
