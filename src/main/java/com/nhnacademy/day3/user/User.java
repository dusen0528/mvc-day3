package com.nhnacademy.day3.user;

public class User {
    private String userName;
    private int userAge;


    public User(){
    }

    public User(String userName, int userAge){
        this.userAge = userAge;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString(){
        return "User{"+
                "userName="+userName+'\''+
                ", userAge=" +userAge +
                '}';
    }
}
