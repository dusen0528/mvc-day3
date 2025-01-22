package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.annotation.RequestMapping;
import com.nhnacademy.day3.student.controller.Command;
import com.nhnacademy.day3.student.domain.Student;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@RequestMapping(value = "/student/view.do", method= RequestMapping.Method.GET)
public class StudentViewController implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StudentRepository studentRepository = (StudentRepository) request.getServletContext().getAttribute("studentRepository");

        String id = request.getParameter("id");
        // id null check
        if(id==null || id.isEmpty()){
            throw new RuntimeException("id is required!");
        }
        // student 조회
        Student student = studentRepository.getStudentById(id);
        if(student==null){
            throw new RuntimeException("Student not found: "+id);
        }

        request.setAttribute("student", student);

        request.setAttribute("update_link", "/student/update?id="+id);
        // /student/view.jsp <-- forward

//        req.getRequestDispatcher("/student/view.jsp").forward(req,resp);
        return "/student/view.jsp";
//        return "/WEB-INF/views/student/view.jsp";
    }
}
