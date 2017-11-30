<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆页面</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js" type="text/javascript"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/style.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style_grey.css" />
<style>
input[type=text] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

input[type=password] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

#loginform\:codeInput {
	margin-left: 1px;
	margin-top: 1px;
}

#loginform\:vCode {
	margin: 0px 0 0 60px;
	height: 34px;
}
</style>
<script type="text/javascript">
	function login() {
$("form").submit();

    }
</script>
</head>
<body>
	<div style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: -280px;">

	</div>
	<div class="main-inner" id="mainCnt"
		style="width: 900px; height: 440px; overflow: hidden; position: absolute; left: 50%; top: 50%; margin-left: -450px; margin-top: -220px;">
		<div id="loginBlock" class="login"
			style="margin-top: 80px; height: 255px;">
			<div class="loginFunc">
				<div id="lbNormal" class="loginFuncMobile">管理员登录</div>
			</div>
			<div class="loginForm">
				<form id="loginform" name="loginform" method="post" class="loginform" action="${pageContext.request.contextPath }/userAction_login.action">
					<div id="idInputLine" class="loginFormIpt showPlaceholder"
						style="margin-top: 5px;">
						<input id="idInput" type="text" name="username"
							class="loginFormTdIpt" maxlength="50" />
						<label for="idInput" class="placeholder" id="username" name="username">帐号：</label>
					</div>
					<div class="forgetPwdLine"></div>
					<div id="pwdInputLine" class="loginFormIpt showPlaceholder">
						<input id="pwdInput" class="loginFormTdIpt" type="password"
							name="password" value="" />
						<label for="pwdInput" class="placeholder" id="password" name="password">密码：</label>
					</div>
					<div class="loginFormIpt loginFormIptWiotTh"
						style="margin-top:58px;">

						<span class="btn btn-login"
							style="margin-top:-36px;" onclick="login();">登录</span>
						</a>
					</div>
					<div style="margin-top: -80px;margin-left: 45px">
						<font color="red">
							<s:actionerror/>
						</font>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>