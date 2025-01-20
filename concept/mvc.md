# MVC Pattern

## Model1
![스크린샷 2025-01-20 오전 10.58.48.png](../../../../../../var/folders/lf/y10g6sqx1r31qpbktbvwk_f00000gn/T/TemporaryItems/NSIRD_screencaptureui_YsE348/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-01-20%20%EC%98%A4%EC%A0%84%2010.58.48.png)
- JSP 페이지가 모든 로직과 출력을 담당
- 비즈니스 로직과 화면 출력 코드가 하나의 JSP에 혼재
- 코드 가독성 저하 , 유지보수 어려움

## Model2
![스크린샷 2025-01-20 오전 10.59.31.png](../../../../../../var/folders/lf/y10g6sqx1r31qpbktbvwk_f00000gn/T/TemporaryItems/NSIRD_screencaptureui_FzDT3p/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-01-20%20%EC%98%A4%EC%A0%84%2010.59.31.png)
- 서블릿이 모든 요청을 받아 처리하고 JSP로 포워딩
- 클라이언트 요청을 서블릿에서 구분해서 처리
- MVC 패턴의 기초가 됨


## Model2 방식 == MVC Pattern
![스크린샷 2025-01-20 오전 11.00.23.png](../../../../../../var/folders/lf/y10g6sqx1r31qpbktbvwk_f00000gn/T/TemporaryItems/NSIRD_screencaptureui_GwgVu9/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-01-20%20%EC%98%A4%EC%A0%84%2011.00.23.png)

- Model : 비즈니스 로직과 데이터 처리 담당
- View : 처리된 결과를 화면에 표시
- Controller : 요청 처리 후 전체 흐름 제어

### 이렇게 하면?
- 유연하고 확장이 용이하며
- 팀단위 협업이 수월해지고
- 유지보수가 쉬워짐

그렇지만 Servlet을 학습하다보면 중복 코드가 많이 보이게 된다

**예시코드**
```
resp.setContentType("text/html");
resp.setCharacterEncoding("UTF-8");

// ...

try {
    RequestDispatcher rd = req.getRequestDispatcher("...");
    rd.forward(req, resp);
    // resp.sendRedirect("...");
} catch (ServletException | IOException ex) {
    log.error("", ex);
}
```

## 공통 처리 부분과 그렇지 않은 부분을 분리해보자

### 공통 처리 부분

**FrontServlet**
![스크린샷 2025-01-20 오전 11.02.40.png](../../../../../../var/folders/lf/y10g6sqx1r31qpbktbvwk_f00000gn/T/TemporaryItems/NSIRD_screencaptureui_T8CUyx/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-01-20%20%EC%98%A4%EC%A0%84%2011.02.40.png)

1. 모든 요청을 Servlet이 다 받는데, 실제 요청이랑은 어떻게 구분하나?
   - FrontServlet이 받을 요청은 .do 확장자를 사용
   - 실제 요청은 .do 확장자가 없음
   - Ex)
     - /boys.do : FrontServlet이 처리
     - /boys : 실제 요청은 Servlet이 처리
2. URL에 따라 적절한 Servlet으로 요청 전달
3. 실제 요청을 처리한 Servlet은 처리 결과를 어떤 jsp에 view를 할건지 반환
4. 실제 요청을 처리한 Servlet이 전달해 준 jsp로 view 처리를 위임
   - 에러 발생시 error page로 지정된 jsp에 view 처리 위임
5. JSP는 실제 요청을 처리한 Servlet에서 ServletRequest에서 설정한 속성을 이용해 View처리 수행
6. FrontServlet이 클라이언트에게 결과 응답

## 클라이언트의 요청부터 응답까지의 흐름
![스크린샷 2025-01-20 오전 11.14.08.png](../../../../../../var/folders/lf/y10g6sqx1r31qpbktbvwk_f00000gn/T/TemporaryItems/NSIRD_screencaptureui_zsTlVv/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-01-20%20%EC%98%A4%EC%A0%84%2011.14.08.png)

### 1단계: URL 확인 (파란 영역)
* 클라이언트가 보낸 요청의 URL이 .do로 끝나는지 확인
* FrontServlet이 처리할 요청인지 판단

### 2단계: 서블릿 매핑 (연두 영역)
* .do로 끝나는 URL을 실제 처리할 서블릿 경로로 변환
* 예시: /boys.do → /boys

### 3단계: 실제 요청 처리 (빨간 영역)
* 변환된 경로의 실제 서블릿으로 요청 전달
* 해당 서블릿에서 비즈니스 로직 처리
* 처리 결과와 보여줄 JSP 페이지 경로를 지정

### 4단계: 화면 처리 (초록 영역)

**정상 처리된 경우**
* 지정된 JSP로 요청 전달
* JSP에서 전달받은 데이터로 화면 생성

**오류 발생한 경우**
* error.jsp로 전달
* 에러 화면 생성

### 5단계: 응답 전송 (주황 영역)
* 생성된 최종 HTML을 클라이언트에게 전송
* 모든 처리 완료
