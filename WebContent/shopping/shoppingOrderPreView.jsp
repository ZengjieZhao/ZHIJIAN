<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	$(function() {

		function setTotal() {
			var s = 0;
			$(".productTr").each(function() {
				var n = $(this).find('input[class=text_box]').val();
				var price = $(this).find('label[class=price_]').html();
				s += n * price;
			});
			$("#product_total").html(s);
		}
		setTotal();

	});
	function balanceOrder(oid){
		var fm = document.createElement("form");
		document.body.appendChild(fm);
		fm.action = "order_balance.action?oid="+oid;
		fm.method = "get";
		fm.submit();
	}
</script>
</head>
<body style="">
	<table>
	<tr><td colspan="2"><span style="font-size:16px;color: red;font-weight:bold;">我的订单</span></td></tr>
	<tr><td>真实姓名:</td><td align="right">${currentUser.trueName }</td></tr>
	<tr><td>联系电话:</td><td align="right">${currentUser.mobile }</td></tr>
	<tr><td>地址:</td><td align="right">${currentUser.address }</td></tr>
	</table>
	<div id="shopping">

		<table id="myTableProduct">
			<tr>
				<th>商品名称</th>
				<th>商品单价</th>
				<th>金额</th>
				<th>购买数量</th>
			</tr>
			<c:forEach items="${orderItemList }"
				var="Item">
				<tr class="productTr">
					<td class="thumb"><img class="imgs"
						src="${Item.product.proPic }" /><a
						href="product_showProduct.action?productId=${Item.product.id }"
						target="_blank">${fn:substring(Item.product.name,0,20)}</a>
					</td>
					<td class="price"><span>￥<label class="price_"
							id="price_${Item.product.id }">${Item.product.price }</label></span>
					</td>
					<td class="price"><span>￥<label
							id="productItem_total_${Item.product.id }">${Item.product.price*Item.num }</label></span>
					</td>
					<td class="number"><input type="hidden" id="product_id"
						value="${Item.product.id }" /><input class="text_box"
						style="width: 30px; text-align: center" name="" type="text"
						value="${Item.num }" readonly="readonly"/> 
				</tr>
			</c:forEach>
		</table>

		<div class="submitOrder">
			<em style="float:right; font-size:14px;"><label>商品金额总计：￥</label><label id="product_total"></label></em>
			<input type="submit" value="结算" onclick="javascript:balanceOrder('${oid}')"
				class="ebutton red" />
		</div>
	</div>
</body>
</html>