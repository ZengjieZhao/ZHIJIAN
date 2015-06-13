<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/buttons.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script type="text/javascript">
	/*表单提交验证*/
	function checkForm() {
		var userName = $("#userName").val();
		var password = $("#password").val();
		var imageCode = $("#imageCode").val();
		if (userName == "") {
			$("#userNameError").html('用户名不能为空！');
			return false;
		}
		$("#userNameError").html('');
		if (password == "") {
			$("#passwordError").html("密码不能为空！");
			return false;
		}
		$("#passwordError").html('');
		if (imageCode == "") {
			$("#imageCodeError").html("验证码不能为空！");
			return false;
		}
		$("#imageCodeError").html('');
		return true;
	}
	if ('${error}' != '') {
		alert('${error}');
	}
	function loadimage() {
		var src = '${pageContext.request.contextPath}/image.jsp?time='
				+ new Date();
		var img = document.getElementById('randImage');
		img.src = src;
	}

</script>
<style type="text/css">
body {
}

.error {
	color: red;
}
</style>
</head>
<body style="background-image: url('../images/bg.jpg')  ; background-size :100% 100% ; background-repeat:no-repeat">

	<div id="loginAdmin" class="wrap">
		<div class="shadow">
			<em class="corner lb"></em> <em class="corner rt"></em>
			<div class="box">
				<h1>至简商城后台管理登录</h1>
				<form id="loginForm" method="post"
					action="${pageContext.request.contextPath}/user_loginAdmin.action"
					onsubmit="return checkForm()">
					<table>
						<tr>
							<td class="field">用户名：</td>
							<td><input class="text" type="text"
								onfocus="javascript: $('#userNameError').html('');"
								id="userName" name="admin.name" value="${admin.name }" /><font
								id="userNameError" color="red"></font></td>
						</tr>
						<tr>
							<td class="field">登录密码：</td>
							<td><input class="text" type="password" onfocus="javascript: $('#passwordError').html('');" id="password"
								name="admin.password" value="${admin.password }" /><font
								id="passwordError" color="red"></font></td>
						</tr>
						<tr>
							<td class="field">验证码：</td>
							<td><input class="text"
								style="width: 60px; margin-right: 10px;" type=text onfocus="javascript: $('#imageCodeError').html('');"
								value="${imageCode }" name="imageCode" id="imageCode"><img
								onclick="javascript:loadimage();" title="换一张试试" name="randImage"
								id="randImage"
								src="${pageContext.request.contextPath}/image.jsp" width="60"
								height="25" border="1" align="absmiddle"
								style="cursor: pointer;"> <label style="cursor: pointer;"
								onclick="javascript:loadimage();">换一张试试</label> <font
								id="imageCodeError" color="red"></font></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" name="submit" value="立即登录"
								style="cursor: pointer;" class="ebutton white" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div style="margin:0 auto; margin-top:300px;width:300px;">Copyright &copy; 2014 ZHIJIAN All Rights Reserved.</div>
</body>
</html>