# 컨트롤러 팩토리 (Controller Factory) 

## 기존 문제점
1. Request Mapping이 추가될 때마다 path와 command를 수동으로 매핑해야 함
2. 요청이 올 때마다 새로운 Command 객체를 생성하는 비효율
3. if-else 구문이 길어져 유지보수가 어려움

## 개선 방안

### 1. @RequestMapping 어노테이션 도입
- 컨트롤러 클래스에 직접 매핑 정보를 명시
- HTTP 메서드(GET/POST)와 경로를 어노테이션으로 관리
- 코드 가독성과 유지보수성 향상

### 2. 컨트롤러 객체 생명주기 개선
- 애플리케이션 시작 시 한 번만 컨트롤러 객체 생성
- ConcurrentMap을 사용하여 컨트롤러 인스턴스 캐싱
- 메모리 사용 효율화 및 성능 향상

### 3. 자동화된 컨트롤러 등록
- 애플리케이션 시작 시 Command 인터페이스 구현체 자동 탐색
- ServletContainerInitializer를 통한 초기화
- 수동 등록 과정 제거

### 4. 컨트롤러 조회 방식 개선
- method + servletPath를 키로 사용하여 컨트롤러 조회
- 맵 기반의 빠른 조회
- 조건문 제거로 코드 간소화

## 장점
1. 선언적 매핑으로 코드 가독성 향상
2. 객체 재사용으로 성능 개선
3. 자동화된 등록으로 개발 편의성 증가
4. 유지보수 용이성 향상

---
기존 student 패키지 안에 구조 
```
src/main/java/com/nhnacademy/day3/student/
├── controller/
│   └── Command.java (인터페이스)
├── annotation/
│   └── RequestMapping.java (어노테이션 정의)
├── controller/
│   ├── StudentListController.java
│   ├── StudentViewController.java
│   ├── StudentDeleteController.java
│   ├── StudentUpdateFormController.java
│   ├── StudentUpdateController.java
│   ├── StudentRegisterFormController.java
│   ├── StudentRegisterController.java
│   └── ErrorController.java
├── config/
│   └── WebAppInitializer.java (서블릿 초기화)
└── factory/
    └── ControllerFactory.java (컨트롤러 팩토리)
```

## 1. annotation/ 디렉토리
- 예: `@RequestMapping` - URL 경로와 HTTP 메서드 매핑 정보를 정의
- 어플리케이션의 선언적 메타데이터 관리

## 2. config/ 디렉토리
- 어플리케이션 설정 관련 클래스 포함
- 서블릿컨테이너 초기화, 빈 설정, 웹 설정 등 담당
- 예: `WebAppInitializer` - 어플리케이션 시작점과 초기 설정 담당

## 3. controller/ 디렉토리
- 실제 요청을 처리하는 컨트롤러 클래스들이 위치
- HTTP 요청을 받아 적절한 서비스 로직을 호출하고 뷰를 결정
- Command 인터페이스와 이를 구현한 각종 컨트롤러들 포함

## 4. factory/ 디렉토리
- 객체 생성과 관리를 담당하는 팩토리 클래스들이 위치
- 예: `ControllerFactory` - 컨트롤러 객체의 생성과 관리를 담당
- 싱글톤 패턴과 객체 재사용을 통한 리소스 관리

## 5. domain/ 디렉토리
- 비즈니스 도메인 객체들의 정의
- 데이터 모델과 관련된 클래스들이 위치
- 예: Student, Command 인터페이스 등

## 6. repository/ 디렉토리
- 데이터 접근 계층을 담당
- 데이터베이스나 파일 시스템과의 상호작용을 처리
- 데이터의 CRUD 연산 구현

## 7. servlet/ 디렉토리
- 서블릿 관련 클래스들이 위치
- 예: FrontServlet - 모든 요청을 받아 적절한 컨트롤러에 위임

---
## 기존 FrontSerlvet에서는..
```java
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
```
- RequestMapping 추가시마다 path에 실행할 command 맵핑 
- 요청될 때마다 Command 객체 생성

### 개선

## RequestMapping.java
```java
package com.nhnacademy.day3.student.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    enum Method{
        POST,GET
    }
    String value();
    Method method() default Method.GET;
}
```

## ErrorController.java
```
package com.nhnacademy.day3.student.servlet;

import com.nhnacademy.day3.student.annotation.RequestMapping;
import com.nhnacademy.day3.student.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RequestMapping(value = "/error.do")
public class ErrorController implements Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("status_code", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        request.setAttribute("exception_type", request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE));
        request.setAttribute("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        request.setAttribute("exception", request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
        request.setAttribute("request_uri", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

        return "/error.jsp";
    }
}
```

- 이와 같이 각 컨트롤러에 어노테이션 추가 

## WebAppInitializer 설정

