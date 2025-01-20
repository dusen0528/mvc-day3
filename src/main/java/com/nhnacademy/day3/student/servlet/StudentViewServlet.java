package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.domain.Student;
import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="studentViewServlet", urlPatterns = "/student/view")
public class StudentViewServlet extends HttpServlet {
    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        // id null check
        if(id==null || id.isEmpty()){
            throw new ServletException("id is required!");
        }
        // student 조회
        Student student = studentRepository.getStudentById(id);
        if(student==null){
            throw new ServletException("Student not found: "+id);
        }

        req.setAttribute("student", student);

        req.setAttribute("update_link", "/student/update?id="+id);
        // /student/view.jsp <-- forward

        req.getRequestDispatcher("/student/view.jsp").forward(req,resp);

    }


}
