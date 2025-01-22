package com.nhnacademy.day3.user;

public class DiTest {
    public static void main(String[] args) {
        UserService userService = InjectUtil.getObject(UserService.class);

        User user = new User("inho1", 10);

        userService.addUser(user);

        System.out.println(userService.getUser("inho1"));;
    }
}
