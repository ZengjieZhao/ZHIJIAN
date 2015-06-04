<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
if('${currentAdmin.name}' ==''){
	alert('管理员未登录！请登录后操作');
	window.parent.frames.location.href="login.jsp"; 
}
	var url;
	
	function formatStatus(val,row){
		if(val==1){
			return "普通管理员";
		}else if(val==2){
			return "系统管理员";
		}
		return "";
	}
	
	function searchAdmin(){
		$("#dg").datagrid('load',{
			"s_admin.name":$("#s_adminName").val()
		});
	}
	
	function deleteAdmin(){
		//1取选择行
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		//获取id
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		//ajax传递删除的id组
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("user_deleteAdmin.action",{ids:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","数据已成功删除！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示","数据删除失败！");
					}
				},"json");
			}
		});
		
	}
	
	
	function openAdminAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加管理员信息");
		url="user_saveAdmin.action";
	}
	
	
	function saveAdmin(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				if($("#auth").combobox("getValue")==""){
					$.messager.alert("系统提示","请选择管理员角色");
					return false;
				}
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示","保存失败,账户已存在！");
					return;
				}
			}
		});
	}
	
	function openAdminModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑管理员信息");
		$("#adminName").val(row.name);
		$("#password").val(row.password);
		$("#auth").combobox("setValue",row.auth);
		url="user_saveAdmin.action?admin.id="+row.id;
	}
	
	function resetValue(){
		$("#adminName").val("");
		$("#password").val("");
		$("#auth").combobox("setValue","");
	}
	
	function closeAdminDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
</script>
</head>
<body style="margin:1px;">
	<table id="dg" title="管理员管理" class="easyui-datagrid"
	 fitColumns="true" pagination="true" rownumbers="true"
	 url="user_adminList.action" fit="true" toolbar="#tb">
	 <thead>
	 	<tr>
	 		<th field="cb" checkbox="true" align="center"></th>
	 		<th field="id" width="50" align="center">ID</th>
	 		<th field="name" width="100" align="center">账户名</th>
	 		<th field="password" width="100" align="center">密码</th>
	 		<th field="auth" width="100" align="center" formatter="formatStatus">权限</th>
	 	</tr>
	 </thead>
	</table>
	<div id="tb">
		<div>
			<a href="javascript:openAdminAddDialog()" class="easyui-linkbutton" iconCls="icon-administrator-add" plain="true">添加</a>
			<a href="javascript:openAdminModifyDialog()" class="easyui-linkbutton" iconCls="icon-administrator-edit" plain="true">修改</a>
			<a href="javascript:deleteAdmin()" class="easyui-linkbutton" iconCls="icon-administrator-delete" plain="true">删除</a>
			&nbsp;账户名：&nbsp;<input type="text" id="s_adminName" size="20" onkeydown="if(event.keyCode==13) searchAdmin()"/>
			<a href="javascript:searchAdmin()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 570px;height:300px;padding: 10px 20px"
	  closed="true" buttons="#dlg-buttons">
	 	<form id="fm" method="post">
	 		<table cellspacing="8px">
	 			<tr>
	 				<td>账户名：</td>
	 				<td><input type="text" id="adminName" name="admin.name" class="easyui-validatebox" required="true"/></td>
	 			</tr>
	 			<tr>
	 				<td>密码：</td>
	 				<td><input type="text" id="password" name="admin.password" class="easyui-validatebox" required="true"/></td>
	 			</tr>
	 			<tr>
	 				<td>角色：</td>
	 				<td>
	 					<select class="easyui-combobox" id="auth" name="admin.auth" style="width: 154px;" editable="false" panelHeight="auto">
	 						<option value="">请选择管理员角色</option>
	 						<option value="2">系统管理员</option>
	 						<option value="1">普通管理员</option>
	 					</select>
	 				</td>
	 			</tr>
	 		</table>
	 	</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveAdmin()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeAdminDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>