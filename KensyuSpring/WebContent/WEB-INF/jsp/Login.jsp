<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
<p style="color: red">${ msg }</p>
<f:form modelAttribute="LoginForm" action="LoginAction" method="post">
<p>ユーザー名：<input type="text" name="userName"></p>
<p>パスワード：<input type="text" name="pass"></p>
<input type="submit" value="ログイン">
</f:form>
</body>
</html>