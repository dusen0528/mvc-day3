package com.nhnacademy.day3.student.servlet;

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
            // 실제 요청 처리할 servlet을 결정
            String servletPath = resolveServlet(req.getServletPath());
            RequestDispatcher rd = req.getRequestDispatcher(servletPath);
            rd.include(req, resp);

            // 실제 요청을 처리한 servlet이 'view'라는 request 속성값으로 view를 전달해 줌
            String view = (String) req.getAttribute("view");
            if (view.startsWith(REDIRECT_PREFIX)) {
                log.error("redirect-url : {}", view.substring(REDIRECT_PREFIX.length()));
                // `redirect:`로 시작하면 redirect 처리
                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()));
            } else {
                // redirect 아니면 JSP에게 view 처리를 위임하여 그 결과를 include시킴
                rd = req.getRequestDispatcher(view);
                rd.include(req, resp);
            }
        } catch (Exception ex) {
            // 공통 error 처리 - ErrorServlet 참고해서 처리
            log.error("Error occurred: ", ex);
            req.setAttribute("exception", ex);
            req.setAttribute("error_message", ex.getMessage());

            // forward - /error.jsp
            RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
            rd.forward(req, resp);
        }
    }

    private String resolveServlet(String servletPath) {
        String processingServlet = null;
        if ("/student/list.do".equals(servletPath)) {
            processingServlet = "/student/list";
        } else if ("/student/view.do".equals(servletPath)) {
            processingServlet = "/student/view";
        } else if ("/student/update.do".equals(servletPath)) {
            processingServlet = "/student/update";
        } else if ("/student/delete.do".equals(servletPath)) {
            processingServlet = "/student/delete";
        } else if ("/student/register.do".equals(servletPath)) {
            processingServlet = "/student/register";
        }
        // 실행할 servlet 결정하기
        return processingServlet;
    }
}
