<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/LodopFuncs.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-barcode-last.min.js"></script>

<script type="text/javascript">

	$(function() {
		$("#jycode").barcode("jy${p_order.orderNo}", "code128", {
			barWidth : 1,
			barHeight : 30,
			showHRI : true
		});
		$("#ordercode").barcode("${p_order.orderNo}", "code128", {
			barWidth : 1,
			barHeight : 30,
			showHRI : true
		});
		$("#lower").html(convertCurrency('${p_order.cost }')); 
	});
	var LODOP;
	function preview() {
		CreateOneFormPage();
		LODOP.PREVIEW();
	};
	function CreateOneFormPage() {
		LODOP = getLodop();
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
		//LODOP. SET_PRINT_PAGESIZE (2, 0, 0,"A4");
		LODOP.SET_PRINT_STYLE("FontSize", 18);
		LODOP.SET_PRINT_STYLE("Bold", 1);
		LODOP.ADD_PRINT_TEXT(50, 231, 260, 39, "打印页面部分内容");
		LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%", document
				.getElementById("invoicePage").innerHTML);
	};
	function convertCurrency(currencyDigits) {
		// Constants:
		var MAXIMUM_NUMBER = 99999999999.99;
		// Predefine the radix characters and currency symbols for output:
		var CN_ZERO = "零";
		var CN_ONE = "壹";
		var CN_TWO = "贰";
		var CN_THREE = "叁";
		var CN_FOUR = "肆";
		var CN_FIVE = "伍";
		var CN_SIX = "陆";
		var CN_SEVEN = "柒";
		var CN_EIGHT = "捌";
		var CN_NINE = "玖";
		var CN_TEN = "拾";
		var CN_HUNDRED = "佰";
		var CN_THOUSAND = "仟";
		var CN_TEN_THOUSAND = "万";
		var CN_HUNDRED_MILLION = "亿";
		var CN_SYMBOL = "人民币";
		var CN_DOLLAR = "元";
		var CN_TEN_CENT = "角";
		var CN_CENT = "分";
		var CN_INTEGER = "整";

		// Variables:
		var integral; // Represent integral part of digit number.
		var decimal; // Represent decimal part of digit number.
		var outputCharacters; // The output result.
		var parts;
		var digits, radices, bigRadices, decimals;
		var zeroCount;
		var i, p, d;
		var quotient, modulus;

		// Validate input string:
		currencyDigits = currencyDigits.toString();
		if (currencyDigits == "") {
		alert("Empty input!");
		return "";
		}
		if (currencyDigits.match(/[^,.\d]/) != null) {
		alert("Invalid characters in the input string!");
		return "";
		}
		if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		alert("Illegal format of digit number!");
		return "";
		}

		// Normalize the format of input digits:
		currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
		currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
		// Assert the number is not greater than the maximum number.
		if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		alert("Too large a number to convert!");
		return "";
		}

		// Process the coversion from currency digits to characters:
		// Separate integral and decimal parts before processing coversion:
		parts = currencyDigits.split(".");
		if (parts.length > 1) {
		integral = parts[0];
		decimal = parts[1];
		// Cut down redundant decimal digits that are after the second.
		decimal = decimal.substr(0, 2);
		}
		else {
		integral = parts[0];
		decimal = "";
		}
		// Prepare the characters corresponding to the digits:
		digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
		radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
		bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
		decimals = new Array(CN_TEN_CENT, CN_CENT);
		// Start processing:
		outputCharacters = "";
		// Process integral part if it is larger than 0:
		if (Number(integral) > 0) {
		zeroCount = 0;
		for (i = 0; i < integral.length; i++) {
		p = integral.length - i - 1;
		d = integral.substr(i, 1);
		quotient = p / 4;
		modulus = p % 4;
		if (d == "0") {
		zeroCount++;
		}
		else {
		if (zeroCount > 0)
		{
		outputCharacters += digits[0];
		}
		zeroCount = 0;
		outputCharacters += digits[Number(d)] + radices[modulus];
		}
		if (modulus == 0 && zeroCount < 4) {
		outputCharacters += bigRadices[quotient];
		}
		}
		outputCharacters += CN_DOLLAR;
		}
		// Process decimal part if there is:
		if (decimal != "") {
		for (i = 0; i < decimal.length; i++) {
		d = decimal.substr(i, 1);
		if (d != "0") {
		outputCharacters += digits[Number(d)] + decimals[i];
		}
		}
		}
		// Confirm and return the final output string:
		if (outputCharacters == "") {
		outputCharacters = CN_ZERO + CN_DOLLAR;
		}
		if (decimal == "") {
		outputCharacters += CN_INTEGER;
		}
		outputCharacters =  outputCharacters;
		return outputCharacters;
		}
</script>
<style type="text/css">
table {
	empty-cells: show;
}

#invoicePage {
	margin: 0 auto;
	font-size: 12px;
}
</style>
</head>
<body>
	<div>
		<a class="easyui-linkbutton" style="cursor: pointer;"
			onclick="preview()">打印</a>
	</div>
	<div id="invoicePage"
		style="padding: 5px; border: 1px solid; width: 90%;">
		<table style="width: 100%;">
			<tr>
				<td width="30%" align="left"><div
						style="width: 168px; height: 50px;">
						<span id="jycode"></span>
					</div></td>
				<td width="30%" align="center"><span style="font-size: 30px;">发货单</span></td>
				<td width="40%" align="center"><span style="font-size: 22px;">至简商城</span></td>
			</tr>
		</table>
		<span>基本信息</span>
		<table
			style="width: 100%; border-collapse: collapse; border: 1px solid #000;">
			<tr>
				<td style="border: 1px solid #000;">商家：至简商贸有限公司</td>
				<td style="border: 1px solid #000;">订单号： ${p_order.orderNo }</td>
				<td style="border: 1px solid #000;">下单时间： ${p_order.createTime }</td>
			</tr>
			<tr>
				<td style="border: 1px solid #000;">用户名：${p_order.user.userName }</td>
				<td style="border: 1px solid #000;">姓名：${p_order.user.trueName }</td>
				<td style="border: 1px solid #000;">电话：${p_order.user.mobile }</td>
			</tr>
			<tr>
				<td style="border: 1px solid #000;">交易方式：货到付款</td>
				<td style="border: 1px solid #000;">配送方式：卖家包邮</td>
				<td style="border: 1px solid #000;">邮编：</td>
			</tr>
			<tr>
				<td colspan="3" style="border-bottom: 1px solid #000;">收货地址：${p_order.user.address}</td>
			</tr>
			<tr>
				<td colspan="3">买家要求：请尽快发货</td>
			</tr>
		</table>
		<span>商品信息</span>
		<table style="width: 100%">
			<tr>
				<td width="10%">ID</td>
				<td width="30%">品名</td>
				<td width="10%">规格</td>
				<td width="10%">单位</td>
				<td width="15%">单价</td>
				<td width="10%">数量</td>
				<td width="5%">折扣</td>
				<td width="15%">小计</td>
			</tr>
		</table>
		<hr>
		<table style="width: 100%;">
			<c:forEach items="${p_order.orderItemList }" var="oi">
				<tr>
					<td width="10%">${oi.product.id }</td>
					<td width="30%">${oi.product.name }</td>
					<td width="10%"></td>
					<td width="10%">件</td>
					<td width="15%">￥${oi.product.price }</td>
					<td width="10%">${oi.num }</td>
					<td width="5%">1</td>
					<td width="15%">￥${oi.product.price*oi.num }</td>
				</tr>
			</c:forEach>
		</table>
		<hr>
		<span>费用信息</span>
		<table
			style="width: 100%; height: 150px; border-collapse: collapse; border: 1px solid #000;">
			<tr>
				<c:set value="0" var="sum" />
				<c:forEach items="${p_order.orderItemList }" var="oi">
					<c:set value="${sum + oi.num}" var="sum" />
				</c:forEach>
				<td width="30%" style="border: 1px solid #000;">商品数量合计：${sum}</td>
				<td width="30%" align="right" style="border-right: 1px solid #000;">货款总额：￥${p_order.cost }</td>
				<td width="160px" rowspan="2"><div style="width: 155px;">
						<span id="ordercode"></span>
					</div></td>
			</tr>
			<tr>
				<td rowspan="3"
					style="border-right: 1px solid #000; border-bottom: 1px solid #000;">商品金额大写：<span id="lower"></span></td>
				<td align="right" style="border-right: 1px solid #000;">邮费：￥0</td>
			</tr>
			<tr>
				<td align="right" style="border-right: 1px solid #000;">订单应收合计：￥${p_order.cost }</td>
				<td rowspan="2">订单复核(签/章)：</td>
			</tr>
			<tr>
				<td align="right" style="border-right: 1px solid #000;">实收金额：￥${p_order.cost }</td>
			</tr>
		</table>
		<table style="width: 100%;">
			<tr>
				<td>操作员：</td>
				<td>收款人：</td>
			</tr>
		</table>
	</div>
</body>
</html>