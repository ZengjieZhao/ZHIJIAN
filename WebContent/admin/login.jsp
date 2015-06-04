<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script type="text/javascript">
	function checkForm(){
		 var userName=$("#userName").val();
		 var password=$("#password").val();
		 var imageCode=$("#imageCode").val();
		 if(userName==""){
			 alert("用户名不能为空！");
			 return false;
		 }
		 if(password==""){
			alert("密码不能为空！");
			 return false;
		 }
		 if(imageCode==""){
			 alert("验证码不能为空！");
			 return false;
		 }
		 return true;
	}
	if('${error}'!=''){
		alert('${error}');
	}
	function loadimage(){
		var src = '${pageContext.request.contextPath}/image.jsp?time='+new Date();
		var img = document.getElementById('randImage');
		img.src = src;
	}
</script>
</head>
<body>
<div id="header" class="wrap">
<div id="logo"><img src="${pageContext.request.contextPath}/images/newlogo.jpg" /></div>
</div>
<div id="loginAdmin" class="wrap">
	<div class="shadow">
		<em class="corner lb"></em>
		<em class="corner rt"></em>
		<div class="box">
			<h1>管理员登陆</h1>
			<form id="loginForm" method="post" action="${pageContext.request.contextPath}/user_loginAdmin.action" onsubmit="return checkForm()">
				<table>
					<tr>
						<td class="field">用户名：</td>
						<td><input class="text" type="text" id="userName" name="admin.name"  value="${admin.name }" /><span></span></td>
					</tr>
					<tr>
						<td class="field">登录密码：</td>
						<td><input class="text" type="password" id="password" name="admin.password"  value="${admin.password }"/><span></span></td>
					</tr>
					<tr>
						<td class="field">验证码：</td>
						<td><input  class="text" style="width: 60px;margin-right: 10px;"
									type=text value="${imageCode }" name="imageCode" id="imageCode"><img
									onclick="javascript:loadimage();" title="换一张试试" name="randImage"
									id="randImage" src="${pageContext.request.contextPath}/image.jsp" width="60" height="25" border="1"
									align="absmiddle" style="cursor: pointer;"> <label style="cursor: pointer;" onclick="javascript:loadimage();">换一张试试</label>				
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td><label class=""><input type="submit" name="submit" value="立即登录" style="cursor: pointer;" class="thoughtbot"/></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2014 ZHIJIAN All Rights Reserved.
</div>
</body>
</html>