<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/buttons.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	
	function checkForm(){
		var userName=$("#userName").val();
		var password=$("#password").val();
		var rePassWord=$("#rePassWord").val();
		var mobile=$("#mobile").val();
		var address=$("#address").val();
		
		if(userName==""){
			$("#error").html("用户名不能为空！");
			return false;
		}
		if(password==""){
			$("#error").html("密码不能为空！");
			return false;
		}
		if(rePassWord==""){
			$("#error").html("确认密码不能为空！");
			return false;
		}
		if(password!=rePassWord){
			$("#error").html("确认密码和密码不一致，请重新输入！");
			return false;
		}
		if(mobile==""){
			$("#error").html("手机号码不能为空！");
			return false;
		}
		if(address==""){
			$("#error").html("收货地址不能为空！");
			return false;
		}
		return true;
	}

	function checkUserName(userName){
		if($("#userName").val()==""){
			$("#userErrorInfo").html("用户名不能为空！");
			$("#userName").focus();
			return;
		}
		$.post("user_existUserWithUserName.action",{userName:userName},
				function(result){
					var result=eval('('+result+')');
					if(result.exist){
						$("#userErrorInfo").html("用户名已存在，请重新输入！");
						$("#userName").focus();
					}else{
						$("#userErrorInfo").html("");
					}
				}
		);
	}
	function clearuserError(){
		$("#userErrorInfo").html("");
	}
</script>
</head>
<body>
<div style="">
<div id="sitenav" class="">
<%@include file="common/sitenav.jsp" %>
</div>
</div>
<div style="clear:both;padding-top:10px;">
</div>
<div id="register" class="format">
	<div class="shado">
		<div class="box">
			<h1 style="background-color: #DD292A;height:50px;line-height:50px;color:white;padding-left:20px;">欢迎注册至简商城会员</h1>
			<form id="regForm" method="post" action="user_register.action" onsubmit="return checkForm()">
				<table>				
					
					<tr>
						<td class="field">用户名(*)：</td>
						<td><input class="text" type="text" id="userName" name="user.userName" onblur="checkUserName(this.value)" onfocus="clearuserError()" />&nbsp;<font id="userErrorInfo" color="red"></font></td>
					</tr>
					<tr>
						<td class="field">登录密码(*)：</td>
						<td><input class="text"  type="password" id="password" name="user.password"   /></td>
					</tr>
					<tr>
						<td class="field">确认密码(*)：</td>
						<td><input class="text" type="password"  id="rePassWord"  name="rePassWord" /></td>
					</tr>
					
					<tr>
						<td class="field">性别(*)：</td>
						<td>
					    <input type="radio"   name="user.sex" value="男" checked="checked"/>男&nbsp;&nbsp;
					    <input type ="radio" name="user.sex" value="女"/>女					    					    
					    </td>						
					</tr>
					<tr>
						<td class="field">出生日期：</td>
						<td>
							<input  type="text"  id="birthday"  name="user.birthday"  class="Wdate" onClick="WdatePicker()"/>	
						</td> 
					</tr>
					<tr>
						<td class="field">身份证号：</td>
						<td><input class="text" type="text" id="dentityCode" name="user.dentityCode"  /></td>
					</tr>
					<tr>
						<td class="field">Email：</td>
						<td><input class="text" type="text" id="email" name="user.email"  /></td>
					</tr>
					<tr>
						<td class="field">手机号码(*)：</td>
						<td><input class="text" type="text" id="mobile" name="user.mobile" /></td>
					</tr>
					<tr>
						<td class="field">收获地址(*)：</td>
						<td><input class="text" type="text" id="address" name="user.address" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" name="submit" class="ebutton green" value="提交注册" /><font id="error" color="red"></font></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>

<div id="footer">
	<jsp:include page="common/footer.jsp"/>
</div>
</body>
</html>