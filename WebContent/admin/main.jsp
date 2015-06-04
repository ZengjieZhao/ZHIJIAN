<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Index</title>
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
	//测试用户权限
	//alert('${currentAdmin.auth}');
	//检测用户是否登录
	if ('${currentAdmin.name}' == '') {
		alert('管理员未登录！请登录后操作');
		location.href = "login.jsp";
	}
	var url;
	function addTab(title, url, iconCls) {
		if ($("#tabs").tabs("exists", title)) {
			$("#tabs").tabs("select", title);
			//refreshTab({tabTitle:title,url:url});  
		} else {
			var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/admin/"
					+ url + "'></iframe>";
			$("#tabs").tabs("add", {
				title : title,
				iconCls : iconCls,
				closable : true,
				content : content
			});
		}
	}

	function refreshTab(cfg) {
		var refresh_tab = cfg.tabTitle ? $('#tabs')
				.tabs('getTab', cfg.tabTitle) : $('#tabs').tabs('getSelected');
		if (refresh_tab && refresh_tab.find('iframe').length > 0) {
			var _refresh_ifram = refresh_tab.find('iframe')[0];
			var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src;
			_refresh_ifram.contentWindow.location.href = refresh_url;
		}
	}
	function openPasswordModifyDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "修改密码");
		url = "user_modifyPassword.action?user.id=${currentUser.id}";
	}

	function closePasswordModifyDialog() {
		$("#dlg").dialog("close");
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#newPassword2").val("");
	}

	function modifyPassword() {
		$("#fm").form("submit", {
			url : url,
			onSubmit : function() {
				var oldPassword = $("#oldPassword").val();
				var newPassword = $("#newPassword").val();
				var newPassword2 = $("#newPassword2").val();
				if (!$(this).form("validate")) {
					return false;
				}
				if (oldPassword != '${currentUser.password}') {
					$.messager.alert("系统提示", "用户密码输入错误！");
					return false;
				}
				if (newPassword != newPassword2) {
					$.messager.alert("系统提示", "确认密码输入错误！");
					return false;
				}
				return true;
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$.messager.alert("系统提示", "密码修改成功，下一次登录生效！");
					closePasswordModifyDialog();
				} else {
					$.messager.alert("系统提示", "密码修改失败");
					return;
				}
			}
		});
	}

	function logout() {
		$.messager.confirm("系统提示", "您确定要退出系统吗", function(r) {
			if (r) {
				window.location.href = "user_logout2.action";
			}
		});
	}

	function refreshSystem() {
		$.post("sys_refreshSystem.action", {}, function(result) {
			if (result.success) {
				$.messager.alert("系统提示", "已成功刷新系统缓存！");
			} else {
				$.messager.alert("系统提示", "刷新系统缓存失败！");
			}
		}, "json");
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north"
		style="height: 50px; background-color: #E0ECFF; overflow: visible;" border="true">
		<table  width="100%">
			<tr>
				<td width="50%" align="left" valign="top">
					<h1 style="color: #0E2D5F;"><span style="font-weight:bold;font-family: '微软雅黑';line-height:10px;">至简网商城后台管理系统</span></h1>
				</td>
				<td valign="middle" align="right" width="50%">
				<span style="font-weight:bold;font-family: '微软雅黑';color: #0E2D5F; "><strong>欢迎：</strong>${currentAdmin.name }</span>
				<a href="javascript:logout()"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-exit'" style="width: auto;">安全退出&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</a>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border="true">
		<div class="easyui-tabs" fit="true" border="true" id="tabs" style="overflow: auto;">
			<div title="管理首页" data-options="iconCls:'icon-home'">
				<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/admin/mainStatistics.jsp'></iframe>
			</div>
		</div>
	</div>
	<div region="west" style="width: 200px" title="导航菜单" border="true" split="true">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="用户管理" data-options="selected:true,iconCls:'icon-userManage'"
				style="padding: 10px">
				<a href="javascript:openPasswordModifyDialog()"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-modifyPassword'"
					style="width: 98.2px;">修改密码</a>
				<c:if test="${currentAdmin.auth == 2}">
					<a href="javascript:addTab('管理员管理','adminManage.jsp','icon-administrator')"
						class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-administrator'"
						style="width: 98.2px;">管理员管理</a>
				</c:if>
				<a href="javascript:addTab('会员管理','userManage.jsp','icon-user')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-user'"
					style="width: 98.2px;">会员管理</a>
			</div>

			<div title="商品管理" data-options="iconCls:'icon-product2'"
				style="padding: 10px;">
				<a
					href="javascript:addTab('商品管理','productManage.jsp','icon-product2')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-manage'"
					style="width: 98.2px;">管理商品</a> <a
					href="javascript:addTab('商品大类管理','productBigTypeManage.jsp','icon-product')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-manage'"
					style="width: 98.2px;">管理商品大类</a> <a
					href="javascript:addTab('商品小类管理','productSmallTypeManage.jsp','icon-product')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-manage'"
					style="width: 98.2px;">管理商品小类</a>
			</div>
			<div title="订单管理" data-options="iconCls:'icon-order'"
				style="padding: 10px">
				<a href="javascript:addTab('订单管理','orderManage.jsp','icon-order')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-manage'"
					style="width: 98.2px;">管理订单</a>
			</div>
			<div title="留言管理" data-options="iconCls:'icon-comment'"
				style="padding: 10px">
				<a
					href="javascript:addTab('留言管理','commentManage.jsp','icon-comment')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-comment'"
					style="width: 98.2px;">管理留言</a>
			</div>
			<div title="公告管理" data-options="iconCls:'icon-notice'"
				style="padding: 10px">
				<a href="javascript:addTab('公告管理','noticeManage.jsp','icon-notice')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-manage'"
					style="width: 98.2px;">管理公告</a>
			</div>
			<div title="新闻管理" data-options="iconCls:'icon-news'"
				style="padding: 10px">
				<a href="javascript:addTab('新闻管理','newsManage.jsp','icon-news')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-news'"
					style="width: 98.2px;">管理新闻</a>
			</div>
			<div title="标签管理" data-options="iconCls:'icon-tagManage'"
				style="padding: 10px">
				<a href="javascript:addTab('标签管理','tagManage.jsp','icon-tag')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-tag'"
					style="width: 98.2px;">管理标签</a>
			</div>
			<div title="统计报表" data-options="iconCls:'icon-detail'">
			</div>
			<div title="系统管理" data-options="iconCls:'icon-item'"
				style="padding: 10px">
				<a href="javascript:refreshSystem()" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-refresh'"
					style="width: auto;">刷新系统缓存</a>
			</div>
		</div>
	</div>
	<div region="south" style="height: 25px; padding: 5px; background-color: #E0ECFF; overflow: visible;" border="true" align="center">
		版权所有 2014 ZHIJIAN 联系方式：赵增杰 13864267332</div>


	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 220px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>用户名：</td>
					<td><input type="text" id="userName" name="user.userName"
						value="${currentAdmin.name }" readonly="readonly"
						style="width: 200px" /></td>
				</tr>
				<tr>
					<td>原密码：</td>
					<td><input type="password" id="oldPassword"
						class="easyui-validatebox" required="true" style="width: 200px" /></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input type="password" id="newPassword"
						name="user.password" class="easyui-validatebox" required="true"
						style="width: 200px" /></td>
				</tr>
				<tr>
					<td>确认新密码：</td>
					<td><input type="password" id="newPassword2"
						class="easyui-validatebox" required="true" style="width: 200px" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:modifyPassword()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a
			href="javascript:closePasswordModifyDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>