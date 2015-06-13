<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>至简购物</title>
</head>
<body>
<div id="sitenav" class="">
<%@include file="common/sitenav.jsp" %>
</div>
<div style="clear: both;"></div>
<div id="header" class="format">
	<jsp:include page="common/top.jsp"/>
</div>

<div id="main" class="format">
	<div id="menu-mng" class="lefter" >
		<div class="box">
			<dl>
				<dt>个人中心</dt>
				<dd><a href="user_getUserInfo.action">个人信息管理</a></dd>
				<dd><a href="order_findOrder.action">个人订单管理</a></dd>
			</dl>
		</div>
	</div>
	<div class="main">
		<jsp:include page="${mainPage }"/>
	</div>
	<div class="clear"></div>
</div>

<div id="footer">
	<jsp:include page="common/footer.jsp"/>
</div>
</body>
</html>