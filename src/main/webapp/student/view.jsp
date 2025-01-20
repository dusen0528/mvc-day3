<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>학생-조회</title>
  <link rel="stylesheet" href="/style.css" />
</head>
<body>
<table>
  <tbody>
  <tr>
    <th>ID</th>
    <td>${student.id}</td>
  </tr>
  <tr>
    <th>이름</th>
    <td>${student.name}</td>
  </tr>
  <tr>
    <th>성별</th>
    <td>${student.gender}</td>
  </tr>
  <tr>
    <th>나이</th>
    <td>${student.age}</td>
  </tr>
  <tr>
    <th>등록일</th>
    <td>${student.createdAt}</td>
  </tr>
  </tbody>
</table>
<ul>
  <li><a href="/student/list">리스트</a></li>
  <li>
    <c:url var="update_link" value="/student/update.do">
      <c:param name="id" value="${student.id}" />
    </c:url>
    <a href="${update_link}">수정</a>
  </li>
  <li>
    <form method="post" action="/student/delete.do">
      <input type="hidden" name="id" value="${student.id}" />
      <button type="submit">삭제</button>
    </form>
  </li>
</ul>
</body>
</html>