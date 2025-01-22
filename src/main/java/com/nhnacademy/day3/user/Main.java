package com.nhnacademy.day3.user;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {

        try{
            Class clazz = Class.forName(User.class.getName());
            Object user = clazz.getDeclaredConstructor().newInstance();
            Method setUserNameMethod = clazz.getDeclaredMethod("setUserName", String.class);
            setUserNameMethod.invoke(user, "최인호");
            Method getUserNameMethod =clazz.getDeclaredMethod("getUserName");
            String userName = (String) getUserNameMethod.invoke(user);
            Method setUserAgeMethod = clazz.getDeclaredMethod("setUserAge", Integer.TYPE);
            setUserAgeMethod.invoke(user,26);
            Method getUserAgeMethod = clazz.getDeclaredMethod("getUserAge");
            int userAge = (int) getUserAgeMethod.invoke(user);

            System.out.println("userName :" + userName);
            System.out.println("userAge :" + userAge);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
