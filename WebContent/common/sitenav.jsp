<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>顶端</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript">
function logout(){
	if(confirm('您确定要退出至简商城吗？')){
		window.location.href="user_logout.action";
	}
}

function checkLogin(){
	if('${currentUser.userName}'==''){
		alert("请先登录！");
	}else{
		window.location.href="shopping_list.action";
	}
}
</script>
</head>
<body>
<div class="sitenav">
<div class="help">
<a href="index.jsp" style="margin-left:6%;margin-right:2%;">首页</a><label style="margin-right:63%;">欢迎来到至简商城网</label>
	<c:choose>
		<c:when test="${not empty currentUser }">
			<a href="shopping_list.action" class="shopping">购物车(${shoppingCart.shoppingCartItems==null?0:shoppingCart.shoppingCartItems.size() }件商品)</a>
			<a href="user_userCenter.action">${currentUser.userName }</a>
			<a href="javascript:logout()">注销</a>
			<a href="register.jsp">注册</a>
			<a href="comment_list.action">留言</a>	
		</c:when>
		<c:otherwise>
			<a href="javascript:checkLogin()" class="shopping">购物车</a>
			<a href="login.jsp">登录</a>
			<a href="register.jsp">注册</a>
		</c:otherwise>
	</c:choose>
</div>
</div>
</body>
</html>