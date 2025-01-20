package com.nhnacademy.day3.student.listener;

import com.nhnacademy.day3.student.domain.Gender;
import com.nhnacademy.day3.student.repository.MapStudentRepository;
import com.nhnacademy.day3.student.domain.Student;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Random;

//application 구동시 student1~student10 학생 등록하기
@WebListener
public class WebApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        StudentRepository studentRepository = new MapStudentRepository();

        Random random = new Random();

        for(int i = 1; i<=10; i++){
            // ... student 1 ~ 10 생성하기
            String id = "student"+i;
            String name = "학생" + i;
            Gender gender = (i%2==0) ? Gender.M:Gender.F;
            // 나이는 20~30 사이 랜덤
            int age = random.nextInt(20, 31);

            Student student = new Student(id, name, gender, age);
            studentRepository.save(student);
        }

        // application scope에 studentRepository 저장
        context.setAttribute("studentRepository", studentRepository);
    }
}
