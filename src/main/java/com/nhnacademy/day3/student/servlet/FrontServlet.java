package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.domain.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/*
모든 *.do 요청을 이 서블릿이 처리합니다.
service 메소드에서 주요 로직을 처리합니다:
공통 설정 (인코딩 등)
실제 처리할 서블릿 결정 (resolveServlet 메소드 사용)
결정된 서블릿으로 요청 위임 (include)
처리 결과에 따라 리다이렉트 또는 뷰 표시
resolveServlet 메소드는 URL에 따라 실제 처리할 서블릿을 결정합니다.
예외 처리를 통해 오류 상황을 일관되게 관리합니다.
 */
@Slf4j
@WebServlet(name = "frontServlet", urlPatterns = "*.do")
public class FrontServlet extends HttpServlet {
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 공통 처리 - 응답 content-type, character encoding 지정
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        try {

            Command command = resolveCommand(req.getServletPath(), req.getMethod());

            if (command == null) {
                throw new ServletException("지원하지 않는 URL 입니다 " + req.getServletPath());
            }
            // Command 실행
            String view = command.execute(req, resp);

            if (view.startsWith(REDIRECT_PREFIX)) {
                log.error("redirect-url : {}", view.substring(REDIRECT_PREFIX.length()));
                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()));
            } else {
                // view 처리를 위임
                RequestDispatcher rd = req.getRequestDispatcher(view);
                rd.include(req, resp);
            }

        } catch (Exception ex) {
            log.error("Error occurred: ", ex);

            // 에러 정보를 직접 설정
            req.setAttribute("status_code", 404); // 또는 적절한 에러 코드
            req.setAttribute("exception_type", ex.getClass().getName());
            req.setAttribute("message", ex.getMessage());
            req.setAttribute("exception", ex);
            req.setAttribute("request_uri", req.getRequestURI());

            RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
            rd.forward(req, resp);
        }
    }

    private Command resolveCommand(String servletPath, String method) {
        Command command = null;

        if ("/error.do".equals(servletPath)) {
            command = new ErrorController();
        } else if ("/student/list.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new StudentListController();
        } else if ("/student/view.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new StudentViewController();
        } else if ("/student/register.do".equals(servletPath)) {
            if ("GET".equalsIgnoreCase(method)) {
                command = new StudentRegisterFormController();
            } else if ("POST".equalsIgnoreCase(method)) {
                command = new StudentRegisterController();
            }
        } else if ("/student/update.do".equals(servletPath)) {
            if ("GET".equalsIgnoreCase(method)) {
                command = new StudentUpdateFormController();
            } else if ("POST".equalsIgnoreCase(method)) {
                command = new StudentUpdateController();
            }
        } else if ("/student/delete.do".equals(servletPath) && "POST".equalsIgnoreCase(method)) {
            command = new StudentDeleteController();
        }

        return command;
    }
}
