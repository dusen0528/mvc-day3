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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name= "studentRegisterServlet", urlPatterns = "/student/register")
public class StudentRegisterServlet extends HttpServlet {

    private StudentRepository studentRepository;


    @Override
    public void init(ServletConfig config) throws ServletException {
        // init studentRepository
        studentRepository =
                (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // /student/register.jsp forward
//        req.getRequestDispatcher("/student/register.jsp").forward(req, resp);
        req.setAttribute("view", "/student/register.jsp");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String genderStr = req.getParameter("gender");
        String ageStr = req.getParameter("age");


        // null check
        if (id == null || name == null || genderStr == null || ageStr == null ||
                id.isEmpty() || name.isEmpty() || genderStr.isEmpty() || ageStr.isEmpty()) {
            throw new ServletException("필수 파라미터가 누락되었습니다.");
        }

        Gender gender = Gender.valueOf(genderStr);
        int age = Integer.parseInt(ageStr);

        Student student = new Student(id, name, gender, age);


        // save 구현
        studentRepository.save(student);

        // redirect /student/view?id=student1
//        resp.sendRedirect("/student/view?id="+id);

        req.setAttribute("view", "redirect:/student/view.do?id=" + student.getId());

    }


}
