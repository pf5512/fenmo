<%@page pageEncoding="utf-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>群组管理</title>
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
		
		//字段的格式化器
		function formatAction(value,row,index){
			var s = '<a href="javascript:void(0);" onclick="editRoom(\''+row.groupId+'\')">编辑群组</a> ';
			return s;
		}

		//编辑新闻
		function editRoom(groupId){
			if(mainId){
				loadRemote(groupId);
			}
			$("#roomWin").window('open');
		}
		//加载数据
		function loadRemote(groupId){
			$("#roomForm").form({
				//加载数据完成后，设置新闻内容
				'onLoadSuccess': function(data){
					 setContent(data.content);
				}
			});
			$("#roomForm").form('load', '../room/getRoomById.do?groupId='+groupId);
		}
		
		//加载内容
		function setContent(content, isAppendTo) {
	        ue.setContent(content , isAppendTo);
	    }
		
		//提交
		function submitForm(state){
			var mainId = $("#mainId").val();
			url = "../room/addNews.do";
			if(mainId){
				url = "../room/updateNews.do";
			}
			$.ajax({
				url:url,
				type:"POST",
				cache:false,
				data:{
					subject:$("#subject").val(),
					description:$("#description").val(),
					roomName:$("#roomName").val(),
					type:$("#type").val(),
					mainId:mainId,
				},
				success:function(data){
					if(data){
						alert("保存成功");
					}
				},
				error:function(error){
				}
			});
		}
	</script>
</head>
<body>
    <div id="searchTool" style="padding:3px">
		<form id="searchForm" >
			<span>群组标题:</span>
			<input name="roomName" style="line-height:26px;border:1px solid #ccc">
			<span>群组类型:</span>
		    <select name="type" class="easyui-combobox" panelHeight="auto" style="width:100px">
		        <option value="0">请选择</option>
				<option value="1">娱乐</option>
				<option value="2">财经</option>
				<option value="3">房地产</option>
				<option value="4">自媒体</option>
			</select>
			<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
		</form>
	</div>
	<div style="margin-bottom:5px">
		<a href="javascript:void(0);" onclick="editNews()" class="easyui-linkbutton" iconCls="icon-add" >新增群组</a>
	</div>
    <table id="fmDataGrid" class="easyui-datagrid" title="DataGrid with Toolbar" style="width:100%;height:380px;"
			data-options="rownumbers:true,singleSelect:true,url:'${pageContext.request.contextPath}/room/searchPageRooms.do',
				pagination:true,method:'get',toolbar:searchTool">
		<thead>
			<tr>
				<th data-options="field:'groupId',hidden:true"></th>
				<th data-options="field:'roomName',width:100,align:'center'">群组名称</th>
				<th data-options="field:'userName',width:100,align:'center'">创建者</th>
				<th data-options="field:'typeStr',width:100,align:'center'">群组类型</th>
				<th data-options="field:'maxusers',width:100,align:'center'">群组上限</th>
				<th data-options="field:'subject',width:160,align:'left'">群组主题</th>
				<th data-options="field:'createdate',width:120,align:'center'">创建时间</th>
				<th field="action" width="100" align="center" formatter="formatAction">操作</th>
			</tr>
		</thead>
    </table>
    <div id="roomWin" class="easyui-window" title="群组" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:800px;padding:0;margin:0;">
		<div class="easyui-panel"  style="width:100%;height:100%;" >
		<div  align="center" style="padding:10px 0;">
		    <form id="roomForm" method="post">
		    	<input type="hidden" id="mainid" name="mainid" /> 
		    	<table cellpadding="5" >
		    		<tr>
		    			<td class="titlespan" >群组名称:</td>
		    			<td align="left" >
		    				<input class="easyui-textbox inputText"  type="text" name="roomName" id="roomName" data-options="required:true" />
	    				</td>
		    		</tr>
		    		<tr>
		    			<td class="titlespan" >群组类型:</td>
		    			<td align="left" >
		    				  <select name="type" name="type" class="easyui-combobox" panelHeight="auto" style="width:100px">
							        <option value="0">请选择</option>
									<option value="1">娱乐</option>
									<option value="2">财经</option>
									<option value="3">房地产</option>
									<option value="4">自媒体</option>
								</select>
	    				</td>
		    		</tr>
		    		<tr>
		    			<td class="titlespan" >群主主题:</td>
		    			<td>
		    				<div>
			    				<input type="text" name="subject">
		    				</div>
		    			</td>
		    		</tr>
		    	    <tr>
		    			<td class="titlespan" >描述:</td>
		    			<td>
		    				<div>
			    				<input type="text" name="description">
		    				</div>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		    <div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitForm(0)">保存</a>
		    </div>
		    </div>
		</div>
	</div>
</body>
</html>