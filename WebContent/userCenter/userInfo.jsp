<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
</head>
<body>
	<h2>用户管理</h2>
	<div class="manage">
		<table class="list">
			<tr>
				<td>ID</td>
				<td class="">${currentUser.id}</td>
			</tr>
			<tr>
				<td>姓名</td>
				<td class="">${currentUser.trueName}</td>
			</tr>
			<tr>
				<td>性别</td>
				<td class="">${currentUser.sex}</td>
			</tr>
			<tr>
				<td>Email</td>
				<td>${currentUser.email}</td>
			</tr>
			<tr>
				<td>手机</td>
				<td class="">${currentUser.mobile}</td>
			</tr>
			<tr>
				<td>余额</td>
				<td class="">${currentUser.balance}</td>
			</tr>
			<tr>
				<td class="" colspan="2"><a href="user_preSave.action">修改</a></td>
			</tr>
		</table>
	</div>
</body>
</html>