<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品详细</title>
<script type="text/javascript">
	function addShoppingCart(productId){
		if('${currentUser.userName}'==''){
			alert("请先登录，然后购物！");
		}else{
			$.post("shopping_addShoppingCartItem.action",{'productId':productId},
				function(result){
					if(result.success){
						alert("已成功加入购物车！");
						location.reload();
					}else{
						alert(result.error);
					}
				}
			);
		}
	}
	
	function goBuy(productId){
		if('${currentUser.userName}'==''){
			alert("请先登录，然后购物！");
			window.location.href="login.jsp";
		}else{
			window.location.href="shopping_buy.action?productId="+productId;
		}
	}
</script>
</head>
<body>
<div id="product"  class="main">
	<h1>${product.name}</h1>
	<div class="infos">
		<div class="thumb">
			<img class="img" src="${product.proPic}" />
		</div>
		<div class="buy">
			<br/>
			<p>
				商城价：<span class="price">￥${product.price}</span>
			</p>
			<p>库 存：${product.stock}</p>
			<br/>
			<div class="button">
				<a href="javascript:goBuy(${product.id })"><img alt="" src="images/buy.jpg"/></a>
				<a href="javascript:addShoppingCart(${product.id })"><img alt="" src="images/cart.jpg"></a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="introduce">
		<h2>
			<strong>商品详情</strong>
		</h2>
		<div class="text">
			${product.description}
		</div>
	</div>
</div>
</body>
</html>