<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/buttons.css" />
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
<div id="position" class="format">
		
</div>

<div class="format">
		<jsp:include page="${mainPage }"/>
</div>
	
<div id="footer">
	<jsp:include page="common/footer.jsp"/>
</div>
</body>
</html>