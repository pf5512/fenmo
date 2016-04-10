<%@page pageEncoding="utf-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>新闻管理</title>
	<link rel="stylesheet" type="text/css" href="./../plugin/easyUI/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./../plugin/easyUI/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./../plugin/easyUI/demo/demo.css">
	<script type="text/javascript" src="./../plugin/easyUI/jquery.min.js"></script>
	<script type="text/javascript" src="./../plugin/easyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="./../plugin/easyUI/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		var toolbar = [{
			text:'Add',
			iconCls:'icon-add',
			handler:function(){alert('add')}
		},{
			text:'Cut',
			iconCls:'icon-cut',
			handler:function(){alert('cut')}
		},'-',{
			text:'Save',
			iconCls:'icon-save',
			handler:function(){alert('save')}
		}];
		
		//查询
		function doSearch(){
			var param = getSearchParam();
			$('#fmDataGrid').datagrid('load',param);
		}
		
		//获取查询参数
		function getSearchParam(){
			var params = $("#searchForm").serializeArray();
			var param = {};
			for ( var i = 0; i < params.length; i++) {
				param[params[i].name] = params[i].value;
			}
			return param;
		}
	</script>
</head>
<body>
	<h2>新闻管理</h2>
	<div class="demo-info" style="margin-bottom:10px">
	</div>
	<table id="fmDataGrid" class="easyui-datagrid" title="DataGrid with Toolbar" style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'../news/getNewsList.do',
				pagination:true,method:'post',toolbar:searchTool">
		<thead>
			<tr>
				<th data-options="field:'title',width:200,align:'center'">新闻标题</th>
				<th data-options="field:'newSrc',width:100,align:'center'">新闻类型</th>
				<th data-options="field:'userName',width:120,align:'center'">作者</th>
				<th data-options="field:'createDate',width:120,align:'center'">创建时间</th>
				<th data-options="field:'zcount',width:60,align:'center'">点赞次数</th>
			</tr>
		</thead>
	</table>
	<div id="searchTool" style="padding:3px">
		<form id="searchForm" >
			<span>新闻标题:</span>
			<input name="title" style="line-height:26px;border:1px solid #ccc">
			<span>新闻类型:</span>
			<input name="newsType" style="line-height:26px;border:1px solid #ccc">
			<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
		</form>
	</div>
</body>
</html>