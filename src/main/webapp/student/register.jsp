<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>학생 등록</title>
  <link rel="stylesheet" href="/style.css" />
</head>
<body>
<h1>학생 등록/수정</h1>
<form method="post" action="<%= request.getAttribute("student") != null ? "/student/update" : "/student/register" %>">
  <table>
    <tr>
      <td>ID</td>
      <td><input type="text" name="id" /></td>
    </tr>
    <tr>
      <td>이름</td>
      <td><input type="text" name="name" /></td>
    </tr>
    <tr>
      <td>성별</td>
      <td>
        <input type="radio" name="gender" value="M" /> 남
        <input type="radio" name="gender" value="F" /> 여
      </td>
    </tr>
    <tr>
      <td>나이</td>
      <td><input type="text" name="age" /></td>
    </tr>
  </table>
  <button type="submit">등록</button>
</form>
</body>
</html>