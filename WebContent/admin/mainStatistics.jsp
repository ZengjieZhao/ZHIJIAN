<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	src="${pageContext.request.contextPath}/js/highcharts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/exporting.js"></script>
<script type="text/javascript">
	$(function() {
		$("#chartOption").combobox({
			onChange : function(n, o) {
				refreshStatistics(2);
			}
		});
		refreshStatistics(1);
		refreshStatistics(2);

	});
	var orderData = new Array();
	var userData = new Array();
	var today = new Array();
	for (var i = 0; i < 24; i++) {
		today.push(i);
	}
	var week = new Array();
	for (var i = 1; i < 8; i++) {
		week.push(i);
	}
	var month = new Array();
	for (var i = 1; i < 31; i++) {
		month.push(i);
	}
	var year = new Array();
	for (var i = 1; i < 13; i++) {
		year.push(i);
	}
	function refreshStatistics(panel) {
		if (panel == 1) {
			$.getJSON('statistic_getMainStatistcsInfo.action', function(data,
					status) {
				if (status == 'success') {
					$('#totalSales').html(data.totalSales);
					$('#lastYearTotalSales').html(
							parseFloat(data.lastYearTotalSales));
					$('#thisYearTotalSales').html(
							parseFloat(data.thisYearTotalSales));
					$('#tNumberOfOrder').html(data.tNumberOfOrder);
					$('#tNumberOfMember').html(data.tNumberOfMember);
					$('#tNumberOfNoBuyMember').html(data.tNumberOfNoBuyMember);
				} else {
					alert('刷新统计数据失败！');
				}

			});
		} else if (panel == 2) {
			var option = $('#chartOption').combobox("getValue");
			var param = {
				'chartOption' : option
			};
			$.getJSON('statistic_getMainChart.action', param, function(data,
					status) {
				if (status == 'success') {
					orderData.length = 0;
					userData.length = 0;
					for (var i = 0; i < data.orderCount.length; i++) {
						orderData.push(data.orderCount[i].count);
						userData.push(data.userCount[i].count);
					}
					var categoriesData;
					if (option == 1) {
						categoriesData = today;
					} else if (option == 2) {
						categoriesData = week;
					} else if (option == 3) {
						categoriesData = month;
					} else if (option == 4) {
						categoriesData = year;
					}
					$('#mainStatistics_panel2_chart').highcharts({
						chart : {
							type : 'line' //指定图表的类型，默认是折线图（line）
						},
						title : {
							text : '会员订单统计' //指定图表标题
						},
						xAxis : {
							categories : categoriesData
						//指定x轴分组
						},
						yAxis : {
							title : {
								text : '数量' //指定y轴的标题
							}
						},
						series : [ { //指定数据列
							name : '订单数', //数据列名
							data : orderData
						} ,{
							name : '会员数',
							data : userData
						}]
					});
				}
			});
		}
	}
</script>
<style type="text/css">
#mainStatistic_panel1 table tr td {
	padding: 8px;
}

* {
	font-size: 12px;
}
</style>
</head>
<body>
	<table style="width: 100%;">
		<tr>
			<td>
				<div id="mainStatistic_panel1"
					style="width: 501px; height: 264px; border: 1px solid #95B8E7; margin: 20px 20px;">
					<div
						style="background-color: #E0ECFF; border-bottom: 1px solid #95B8E7; width: 100%; height: 24px;">
						<span style="line-height: 22px;">商店统计</span><a
							class="easyui-linkbutton" style="float: right;"
							href="javascript:refreshStatistics(1);"
							data-options="plain:true,iconCls:'icon-reload'"></a>
					</div>
					<table width="400px;">
						<tr>
							<td>销售总额：</td>
							<td><span id="totalSales"></span>(元)</td>
						</tr>
						<tr>
							<td>上年全年销售:</td>
							<td><span id="lastYearTotalSales"></span>(元)</td>
						</tr>
						<tr>
							<td>今年全年销售(至今):</td>
							<td><span id="thisYearTotalSales"></span>(元)</td>
						</tr>
						<tr>
							<td>总订单数:</td>
							<td><span id="tNumberOfOrder"></span>(个)</td>
						</tr>
						<tr>
							<td>总会员数:</td>
							<td><span id="tNumberOfMember"></span>(个)</td>
						</tr>
						<tr>
							<td>未购买过商品的会员数:</td>
							<td><span id="tNumberOfNoBuyMember"></span>(个)</td>
						</tr>
					</table>
				</div>
			</td>

			<td>
				<div id="mainStatistics_panel2"
					style="width: 501px; border: 1px solid #95B8E7; margin: 20px 20px;">
					<div
						style="background-color: #E0ECFF; border-bottom: 1px solid #95B8E7; width: 100%; height: 24px;">
						<span style="line-height: 22px;">商店统计表</span> <span
							style="float: right;"><select class="easyui-combobox"
							id="chartOption" name="chartOption" style="width: 154px;"
							editable="false" panelHeight="auto"
							onchange="javascript:refreshStatistics(2)">
								<option value="1">当天</option>
								<!-- <option value="2">本星期</option> -->
								<option value="3">本月</option>
								<option value="4">本年</option>
						</select> </span>
					</div>
					<div id="mainStatistics_panel2_chart"
						style="min-width: 200px; max-width: 500px; height: 240px;"></div>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>