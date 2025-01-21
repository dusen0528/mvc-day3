package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.domain.Command;
import com.nhnacademy.day3.student.domain.Gender;
import com.nhnacademy.day3.student.domain.Student;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class StudentRegisterController implements Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        StudentRepository studentRepository =
                (StudentRepository) request.getServletContext().getAttribute("studentRepository");


        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String genderStr = request.getParameter("gender");
        String ageStr = request.getParameter("age");


        // null check
        if (id == null || name == null || genderStr == null || ageStr == null ||
                id.isEmpty() || name.isEmpty() || genderStr.isEmpty() || ageStr.isEmpty()) {
            throw new RuntimeException("필수 파라미터가 누락되었습니다.");
        }

        Gender gender = Gender.valueOf(genderStr);
        int age = Integer.parseInt(ageStr);

        Student student = new Student(id, name, gender, age);


        // save 구현
        studentRepository.save(student);

        // redirect /student/view?id=student1
//        resp.sendRedirect("/student/view?id="+id);

        return "redirect:/student/view.do?id=" + student.getId();
    }
}
