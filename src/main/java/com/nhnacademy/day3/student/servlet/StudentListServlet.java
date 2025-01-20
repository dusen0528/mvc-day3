package com.nhnacademy.day3.student.servlet;

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
import java.util.List;

@Slf4j
@WebServlet(name = "studentListServlet", urlPatterns = "/student/list")
public class StudentListServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //student list 구하기
        List<Student> studentList = studentRepository.getStudents();

        req.setAttribute("studentList",studentList);

        /*
        /student/list.jsp <- forward 하기
        다른 리소스(JSP, 서블릿 등)로 요청을 전달할 수 있는 RequestDispatcher 객체를 생성
        매개변수로 전달할 리소스의 경로를 지정
        webapp 폴더가 root 경로("/")가 됨
        현재 요청(request)과 응답(response)을 다른 리소스로 전달
        제어권을 완전히 이동시킴 (URL 변경 없음)
         */

//        req.getRequestDispatcher("/student/list.jsp").forward(req,resp);
        req.setAttribute("view", "/student/list.jsp");
    }

}
