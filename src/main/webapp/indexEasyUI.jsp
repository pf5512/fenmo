<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8" />
	<title>Full Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="./plugin/easyUI/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./plugin/easyUI/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./plugin/easyUI/demo/demo.css">
	<script type="text/javascript" src="./plugin/easyUI/jquery.min.js"></script>
	<script type="text/javascript" src="./plugin/easyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	</script>
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
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div data-options="region:'center',title:'面板'">
	   	<table class="easyui-datagrid" title="群组管理" style="width:100%;height:100%;"
			data-options="singleSelect:true,collapsible:true,url:'/fenmo/news/getNews.do',method:'get'">
			<thead>
				<tr>
					<th data-options="field:'mainid',width:100,align:'center',hide:'true'"></th>
					<th data-options="field:'createdate',width:100,align:'center'">创建时间</th>
					<th data-options="field:'userName',width:100,align:'center'">创建者</th>
					<th data-options="field:'content',width:100,align:'center'">新闻内容</th>
				</tr>
			</thead>
	    </table>
	</div>
</body>
</html>