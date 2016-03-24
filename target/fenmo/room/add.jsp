<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/commonForm.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/page/js/system/user/add.js"></script>
</head>
<body>

	<s:form id="contextform">
		<table class="r_table">
			<tr>
				<td class="lable"><span class="message">*</span>用户账号</td>
				<td class="info"><s:textfield id="yhzh" name="record.yhzh" validate="{required:true}" onblur="selectUser()"></s:textfield><span id ="czyhzh"style="color: red; display: none">已存在此账号</span></td>
			</tr>

			<tr>
				<td class="lable"><span class="message">*</span>用户密码</td>
				<td class="info"><s:password id="yhmm" name="record.yhmm" validate="{required:true}"></s:password> </td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td class="lable"><span class="message">*</span>确认密码</td> -->
<%-- 				<td class="info"><s:textfield id="yhmm1" validate="{required:true}"></s:textfield> </td> --%>
<!-- 			</tr> -->
			<tr>
				<td class="lable"><span class="message">*</span>用户姓名</td>
				<td class="info"><s:textfield id="yhxm" name="record.yhxm" validate="{required:true}"></s:textfield></td>
			</tr>
			<tr>
				<td class="lable">用户权限</td>
				<td class="info">
				<select id="power" name="record.power">
					<option value="0">派出所操作员</option>
					<option value="1">分局管理员</option>
					<option value="2">市局管理员</option>
					<option value="3">分局观察员</option>
					<option value="4">市局观察员</option>
				</select>
				 </td>
			</tr>			
			<tr>
				<td class="lable"><span class="message">*</span>用户部门</td>
				<td class="info"><s:textfield id="yhbm_1" validate="{required:true}"></s:textfield></td>
			</tr>
			<tr>
				<td class="lable">联系电话</td>
				<td class="info"><s:textfield id="lxdh" name="record.lxdh"></s:textfield></td>
			</tr>
		</table>
		<s:hidden id="yhbh" name="record.yhbh"></s:hidden>
		<s:hidden id="yhbm" name="record.yhbm"></s:hidden>
		<div id="aaa"></div>
	</s:form>
</body>
</html>