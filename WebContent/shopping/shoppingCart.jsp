<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购买</title>
<script>
$(function(){
	
	$(".add").click(function(){
		var t=$(this).parent().find('input[class=text_box]');
		t.val(parseInt(t.val())+1);
		var product_id=$(this).parent().find('input[id=product_id]').val();
		var price=$("#price_"+product_id).html();
		$("#productItem_total_"+product_id).html(price*t.val());
		
		refreshSession(product_id,t.val());
		setTotal();
	});
	
	$(".min").click(function(){
		var t=$(this).parent().find('input[class=text_box]');
		t.val(parseInt(t.val())-1);
		if(parseInt(t.val())<0){
			t.val(0);
		}
		var product_id=$(this).parent().find('input[id=product_id]').val();
		var price=$("#price_"+product_id).html();
		$("#productItem_total_"+product_id).html(price*t.val());
		
		refreshSession(product_id,t.val());
		setTotal();
	});
	
	$(".text_box").blur(function(){
		var t=$(this).parent().find('input[class=text_box]');
		if(parseInt(t.val())<0){
			t.val(0);
		}
		var product_id=$(this).parent().find('input[id=product_id]').val();
		var price=$("#price_"+product_id).html();
		$("#productItem_total_"+product_id).html(price*t.val());
		
		refreshSession(product_id,t.val());
		setTotal();
	});
	
	function setTotal(){
		var s=0;
		$(".productTr").each(function(){
			var n=$(this).find('input[class=text_box]').val();
			var price=$(this).find('label[class=price_]').html();
			s+=n*price;
		});
		$("#product_total").html(s);
	}
	
	function refreshSession(productId,count){
		$.post("shopping_updateShoppingCartItem.action",{productId:productId,count:count},
			function(result){
				var result=eval('('+result+')');
				if(result.success){
					
				}else{
					alert("刷新Session失败");
				}
			}
		);
	}
	
	setTotal();
	
	
	
});

function removeShopping(productId){
	if(confirm("您确定要删除这个商品吗？")){
		window.location.href="shopping_removeShoppingCartItem.action?productId="+productId;
	}
}	

function submit(){
	var status = $("#status").text();
	if(status==null||status == ""){
		alert("您的购物车内没有商品，欢迎您前去购物！");
		return false;
	}else{
		var fm = document.createElement("form");
		document.body.appendChild(fm);
		fm.action = "order_save.action";
		fm.method = "post";
		fm.submit();
	}
	
	//$("#fm_shopping").submit();
}

</script>
</head>
<body>
	<div id="shopping">
<!-- 				<form id="fm_shopping" action="order_save.action" method="post" onsubmit="return submit()">
 -->			<table id="myTableProduct">
				<tr>
					<th>商品名称</th>
					<th>商品单价</th>
					<th>金额</th>
					<th>购买数量</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${shoppingCart.shoppingCartItems }" var="shoppingCartItem">
				<span id="status">${shoppingCart }</span>
					<tr class="productTr">
						<td class="thumb">
							<img class="imgs" src="${shoppingCartItem.product.proPic }" /><a href="product_showProduct.action?productId=${shoppingCartItem.product.id }" target="_blank">${fn:substring(shoppingCartItem.product.name,0,20)}</a>
						</td>
						<td class="price" ><span>￥<label class="price_" id="price_${shoppingCartItem.product.id }">${shoppingCartItem.product.price }</label></span> 
						</td>
						<td class="price" >
							<span>￥<label id="productItem_total_${shoppingCartItem.product.id }">${shoppingCartItem.product.price*shoppingCartItem.count }</label></span>
						</td>
						<td class="number">
						        <input type="hidden" id="product_id" value="${shoppingCartItem.product.id }"/>
								<input class="min" name="" type="button" value=" - "  /> 
								<input class="text_box"  style="width: 30px;text-align: center" name="" type="text" value="${shoppingCartItem.count }" /> 
								<input class="add" name="" type="button" value=" + " /> 
						</td>
						<td class="delete"><a
							href="javascript:removeShopping(${shoppingCartItem.product.id })">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>

			<div class="button">
				<input type="submit" value="" onclick="javascript:submit()"/>
			</div>
		<!-- </form> -->
</div>

<div class="shopping_list_end">

	<ul>
		<li class="shopping_list_end_2">￥<label id="product_total"></label>
		</li>
		<li class="shopping_list_end_3">商品金额总计：</li>
	</ul>
</div>
</body>
</html>