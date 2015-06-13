<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购买结果</title>
</head>
<body>
	<div class="wrap">
		<div id="shopping">
			<div class="shadow">
				<em class="corner lb"></em> <em class="corner rt"></em>
				<c:out value="${stockstatus}"/>
				<c:out value="${balance}"/>
				<div class="box">
					<c:choose>
						<c:when test="${stockstatus==1 }">
							<c:choose>
								<c:when test="${balance==1 }">
									<div class="msg">
										<p>恭喜：购买成功！</p>
									</div>
								</c:when>
								<c:otherwise>
								<div class="msg">
								<p>余额不足！</p>
								<p><a href="javascript:history.go(-1);">返回订单...</a></p>
							</div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<div class="msg">
								<p>库存不足！</p>
								<p><a href="javascript:history.go(-1);">返回购物车...</a></p>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>


</body>
</html>