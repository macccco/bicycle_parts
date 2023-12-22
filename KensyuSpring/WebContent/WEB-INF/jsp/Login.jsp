<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<script type="text/javascript">
	function loginSubmit(form) {
		let msg = "";
		if(form.elements["userName"].value == "") {
			msg += "ユーザー名を入力してください\n";
		}
		if(form.elements["pass"].value == "") {
			msg += "パスワードを入力してください";
		}
		if(msg != "") {
			alert(msg);
			return false;
		}
		form.submit();
	}
</script>
</head>
<body>
<p style="color: red">${ msg }</p>
<f:form modelAttribute="LoginForm" action="LoginAction" method="post">
<p>ユーザー名：<input type="text" name="userName"></p>
<p>パスワード：<input type="password" name="pass"></p>
<input type="button" value="ログイン" onclick="loginSubmit(this.form)">
</f:form>
</body>
</html>