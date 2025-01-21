package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.domain.Command;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudentDeleteController implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StudentRepository studentRepository = (StudentRepository)request.getServletContext().getAttribute("studentRepository");
        String id = request.getParameter("id");
        studentRepository.deleteById(id);

        return "redirect:/student/list.do";

    }
}
