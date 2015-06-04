<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>顶端</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body>

<div id="logo" style="">
	<a href="index.jsp"><img src="images/newlogo.jpg" width="180px" height="60px"/></a><label style="font-size: 22px; color:#B1191A;">${navCode }</label>
</div>
<div class="help">
	<form action="product.action" method="post">
		<input type="text" id="txtSearch" name="s_product.name"
			onkeyup="" autocomplete="off" value="${s_product.name }" /> <input
			type="submit" id="cmdSearch"  value="搜索" class="button"/><br/>
		<div id="suggest" style="width: 200px"></div>
	</form>
	<div id="childNav">
	<div class="wrap">
		<ul class="clearfix">
			<c:forEach var="tag" items="${tagList }" varStatus="status">
				<c:choose>
					<c:when test="${status.index==0 }">
						<li class="first">
							<a href="${tag.url }" target="_blank">${tag.name }</a>
						</li>
					</c:when>
					<c:otherwise>
						<li>
							<a href="${tag.url }" target="_blank">${tag.name }</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul> 
	</div>
</div>
</div>
<div class="clear"></div>
<div class="navbar">
	<ul class="clearfix">
		<li class="current"><a href="index.jsp">首页</a>
		</li>
		<c:forEach var="bigType" items="${bigTypeList }">
			<li>
				<a href="product.action?s_product.bigType.id=${bigType.id }">${bigType.name }</a>
			</li>
		</c:forEach>
	</ul>
	<hr style="background-color: #B1191A; height: 5px;border: 1px solid #B1191A;">
</div>



</body>
</html>