package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.domain.Command;
import com.nhnacademy.day3.student.domain.Student;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudentUpdateFormController implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StudentRepository studentRepository = (StudentRepository) request.getServletContext().getAttribute("studentRepository");
        // 학생 조회
        String id = request.getParameter("id");
        Student student = studentRepository.getStudentById(id);
        if(student==null){
            throw new RuntimeException("Student not found");
        }

        request.setAttribute("student", student);


        //   RequestDispatcher rd = req.getRequestDispatcher("/student/register.jsp");
        //        rd.forward(req,resp);
        return "/student/register.jsp";
    }

}
