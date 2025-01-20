package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.repository.StudentRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "studentDeleteServlet", urlPatterns = "/student/delete")
public class StudentDeleteServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get parameter  : id , id가 존재하지 않을경우 throw new RuntimeException("...")
        String id = req.getParameter("id");
        if(id.isEmpty() || id ==null){
            throw new ServletException("id is null");
        }

        studentRepository.deleteById(id);
        // /student/list <-- redirect

        resp.sendRedirect("/student/list");

    }
}
