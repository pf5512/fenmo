<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>Full Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="plugin/easyUI/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="plugin/easyUI/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="plugin/easyUI/demo/demo.css">
	<script type="text/javascript" src="plugin/easyUI/jquery.min.js"></script>
	<script type="text/javascript" src="plugin/easyUI/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">north region</div>
	<div data-options="region:'west',split:true,title:'目录树'" style="width:150px;padding:10px;">
	  <div class="easyui-panel" >
		<ul class="easyui-tree">
			<li>
				<span>内容管理</span>
				<ul>
					<li>群组管理</li>
				    <li>新闻管理</li>
				    <li>用户管理</li>
				    <li>动态管理</li>
				</ul>
			</li>
		</ul>
	</div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">底部</div>
	<div data-options="region:'center',title:'面板'">
	   	<table class="easyui-datagrid" title="群组管理" style="width:100%;height:100%;"
			data-options="singleSelect:true,collapsible:true,url:'${pageContext.request.contextPath}/room/searchPageRooms.do',method:'get'">
			<thead>
				<tr>
					<th data-options="field:'groupId',hidden:true"></th>
					<th data-options="field:'roomName',width:100,align:'center'">群组名称</th>
					<th data-options="field:'userName',width:100,align:'center'">创建者</th>
					<th data-options="field:'typeStr',width:100,align:'center'">群组类型</th>
					<th data-options="field:'createdate',width:120,align:'center'">创建时间</th>
					<th data-options="field:'maxusers',width:100,align:'center'">群组上限</th>
					<th data-options="field:'subject',width:160,align:'left'">群组主题</th>
				</tr>
			</thead>
	    </table>
	    <div id="tb" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
			</div>
			<div>
				Date From: <input class="easyui-datebox" style="width:80px">
				To: <input class="easyui-datebox" style="width:80px">
				Language: 
				<select class="easyui-combobox" panelHeight="auto" style="width:100px">
					<option value="java">Java</option>
					<option value="c">C</option>
					<option value="basic">Basic</option>
					<option value="perl">Perl</option>
					<option value="python">Python</option>
				</select>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
			</div>
	   </div>
	</div>
</body>
</html>