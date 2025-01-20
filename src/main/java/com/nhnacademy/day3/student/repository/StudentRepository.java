package com.nhnacademy.day3.student.repository;

import com.nhnacademy.day3.student.domain.Student;

import java.util.List;

public interface StudentRepository {

    //학생-등록
    void save(Student student);
    //학생-수정
    void update(Student student);
    //학생-삭제
    void deleteById(String id);
    //학생-조회_by id
    Student getStudentById(String id);
    //학생-리스트
    List<Student> getStudents();
    //학생-아이디 존재여부
    boolean existById(String id);

}