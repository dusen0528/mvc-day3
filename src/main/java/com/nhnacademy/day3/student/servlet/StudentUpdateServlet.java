package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.domain.Gender;
import com.nhnacademy.day3.student.domain.Student;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="studentUpdateServlet", urlPatterns = "/student/update")
public class StudentUpdateServlet extends HttpServlet {
    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository)config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 학생 조회
        String id = req.getParameter("id");
        Student student = studentRepository.getStudentById(id);
        if(student==null){
            throw new ServletException("Student not found");
        }

        req.setAttribute("student", student);


        //   RequestDispatcher rd = req.getRequestDispatcher("/student/register.jsp");
        //        rd.forward(req,resp);
        req.setAttribute("view","/student/register.jsp");

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String genderStr = req.getParameter("gender");
        String ageStr = req.getParameter("age");

        // null check
        if (id == null || name == null || genderStr == null || ageStr == null) {
            throw new ServletException("parameter is null");
        }

        int age = Integer.parseInt(ageStr);
        Gender gender = Gender.valueOf(genderStr.toUpperCase());

        Student student = new Student(id, name, gender, age);

        // student 저장
        studentRepository.update(student);

//        resp.sendRedirect("/student/view?id="+id);
        req.setAttribute("view", "redirect:/student/view?id="+student.getId());
    }


}
