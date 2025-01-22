package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.annotation.RequestMapping;
import com.nhnacademy.day3.student.controller.Command;
import com.nhnacademy.day3.student.domain.Gender;
import com.nhnacademy.day3.student.domain.Student;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RequestMapping(value = "/student/update", method = RequestMapping.Method.POST)
public class StudentUpdateController implements Command {
    private StudentRepository studentRepository;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StudentRepository studentRepository = (StudentRepository) request.getServletContext().getAttribute("studentRepository");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String genderStr = request.getParameter("gender");
        String ageStr = request.getParameter("age");

        // null check
        if (id == null || name == null || genderStr == null || ageStr == null) {
            throw new RuntimeException("parameter is null");
        }

        int age = Integer.parseInt(ageStr);
        Gender gender = Gender.valueOf(genderStr.toUpperCase());

        Student student = new Student(id, name, gender, age);

        // student 저장
        studentRepository.update(student);

//        resp.sendRedirect("/student/view?id="+id);
        return "redirect:/student/view?id="+student.getId();
    }
}
