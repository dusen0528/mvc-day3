package com.nhnacademy.day3.student.domain;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Student implements Serializable {

    /*
    Web-App을 war로 만들어 서버 업로드 후 사용하는 과정에서 직렬화가 안되어있는 클래스는 war파일 압축 불가
    서버 배포하는 프로그램은 반드시 Serializable를 구현해야함

    디폴트 생성자
    모든 필드 변수에 대해 Getter, Setter 필요

     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private Gender gender;

    private int age;

    private LocalDateTime createdAt;


    public Student(){

    }

    public Student(String id, String name) {
        this(id, name, null, 0);
    }


    public Student(String id, String name, Gender gender, int age){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.age=age;
        createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
