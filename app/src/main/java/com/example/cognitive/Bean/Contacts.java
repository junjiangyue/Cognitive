package com.example.cognitive.Bean;

public class Contacts {
    private String userName;
    private String userPhone;
    private String userSex;
    private String userBirth;
    private int userId;

    public String getUserName(){
        return userName;
    }
    public String getUserPhone(){
        return userPhone;
    }
    public String getUserSex(){
        return userSex;
    }
    public String getUserBirth(){
        return userBirth;
    }
    public int getUserId(){
        return userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserPhone(String userPhone){
        this.userPhone = userPhone;
    }
    public void setUserSex(String userSex){
        this.userSex = userSex;
    }
    public void setUserBirth(String userBirth){
        this.userBirth = userBirth;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public void add(Contacts data) {
        this.userId = data.userId;
        this.userBirth = data.userBirth;
        this.userSex = data.userSex;
        this.userPhone = data.userPhone;
        this.userName = data.userName;
    }
}
