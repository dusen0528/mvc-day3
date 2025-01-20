<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>student - list</title>
  <link rel="stylesheet" href="/style.css" />
</head>

<body>
<h1>학생 리스트</h1>
<p><a href="/student/register" >학생(등록)</a></p>
<table>
  <thead>
  <tr>
    <th>아이디</th>
    <th>이름</th>
    <th>성별</th>
    <th>나이</th>
    <th>cmd</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var = "student" items="${studentList}">
    <tr>
      <td>${student.id}</td>
      <td>${student.name}</td>
      <td>${student.gender}</td>
      <td>${student.age}</td>
      <td>
        <a href="/student/view?id=${student.id}">조회</a>
        <a href="/student/update?id=${student.id}">수정</a>
        <form method="post" action="/student/delete" style="display:inline;">
          <input type="hidden" name="id" value="${student.id}" />
          <button type="submit">삭제</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>