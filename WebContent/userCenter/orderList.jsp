<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
<script type="text/javascript">
	function confirmReceive(orderNo){
		if(confirm("确认收货？")){
			$.post("order_confirmReceive.action",{status:4,orderNo:orderNo},function(result){
				if(result.success){
					alert("确认收货成功！");
					location.reload();
				}else{
					alert("确认收获失败！");
				}
			});
		}
	}
	function cancelOrder(orderNo){
		if(confirm("确认取消订单？")){
			$.post("order_cancelOrder.action",{status:8,orderNo:orderNo},function(result){
				if(result.success){
					alert("取消订单进入审核！");
					location.reload();
				}else{
					alert("取消订单失败！");
				}
			});
		}
	}
	function gotoBalance(orderNo){
		window.location.href="order_gotoBalance.action?orderNo="+orderNo;
	}
	function backOrder(orderNo){
		if(confirm("确认退单 ？")){
			$.post("order_backOrder.action",{status:5,orderNo:orderNo},function(result){
				if(result.success){
					alert("退单待审核！");
					location.reload();
				}else{
					alert("退单失败！");
				}
			});
		}
	}
</script>
</head>
<body>
<h2>订单管理</h2>
		<div class="manage">
			<div class="search">
				<form action="order_findOrder.action" method="post">
					订单号：<input type="text" class="text" name="s_order.orderNo" value="${s_order.orderNo}" /> 
					 <input type="submit" class="ebutton red" style="width:50px;height:25px;line-height: 2px;" name="submit" value="查询" />
				</form>
			</div>
			<div class="spacer"></div>
				<table class="list">
				<c:forEach var="order" items="${orderList }">
				<tr style="">
					<td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					单号：${order.orderNo }
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					下单时间：<fmt:formatDate value="${order.createTime }" type="date" pattern="yyyy-MM-dd"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					状态：
					<c:choose>
						<c:when test="${order.status==0 }">未付款<button onclick="gotoBalance(${order.orderNo})" class="ebutton red" style="width:120px;height:30px;">去付款</button><button onclick="cancelOrder(${order.orderNo})" class="ebutton orange" style="width:120px;height:30px;">取消订单</button></c:when>
						<c:when test="${order.status==1 }">待审核</c:when>
						<c:when test="${order.status==2 }">待发货</c:when>
						<c:when test="${order.status==3 }"><button onclick="confirmReceive(${order.orderNo})" class="ebutton green" style="width:120px;height:30px;">确认收货</button></c:when>
						<c:when test="${order.status==4 }">交易已完成<button onclick="backOrder(${order.orderNo})" class="ebutton gray" style="width:120px;height:30px;">申请退单</button></c:when>
						<c:when test="${order.status==5 }">退单待审核</c:when>
						<c:when test="${order.status==6 }">退单审核通过</c:when>
						<c:when test="${order.status==7 }">已退单</c:when>
						<c:when test="${order.status==8 }">已取消订单</c:when>
					</c:choose>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					总金额：${order.cost }&nbsp;(元)
					</td>
				</tr>
				<c:forEach var="orderItem" items="${order.orderItemList }">
					<tr>
						<td width="50%">
							<a href="product_showProduct.action?productId=${orderItem.product.id }" target="_blank"><img width="72" height="72" src="${orderItem.product.proPic }"></a>
							&nbsp;&nbsp;
							<a href="product_showProduct.action?productId=${orderItem.product.id }" target="_blank">${orderItem.product.name}</a>
						</td>
						<td width="17%">
							&nbsp;&nbsp;
							${orderItem.product.price}
						</td>
						<td width="17%">
							&nbsp;&nbsp;
							${orderItem.num}
						</td>
						<td>
							&nbsp;&nbsp;小计：
							${orderItem.num*orderItem.product.price}&nbsp;(元)
						</td>
					</tr>
				</c:forEach>
				</c:forEach>
				</table>
		</div>
</body>
</html>