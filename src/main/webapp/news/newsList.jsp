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
	
	<script type="text/javascript" charset="utf-8" src="./../plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="./../plugin/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="./../plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
	
    <style type="text/css">
    	.titlespan{
    		width:100px;
    	}
    </style>
	<script type="text/javascript">
	
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
			var s = '<a href="javascript:void(0);" onclick="editNews(\''+row.mainid+'\')">编辑新闻</a> ';
			return s;
		}
		
		//创建富文本框
		var ue = UE.getEditor('editor',{
			elementPathEnabled:false //不显示元素路径
			//UEDITOR_HOME_URL:window.location.protocol+"//"+window.location.host+"/fenmo/plugin/ueditor" 指定根目录
		});
		
		//编辑新闻
		function editNews(mainId){
			clearForm();
			if(mainId){
				loadRemote(mainId);
			}
			$("#newsWin").window('open');
		}
	
		//清除表单数据
		function clearForm(){
			$("#newsForm").form('clear');
			setContent('');
		}
		
		//加载数据
		function loadRemote(mainId){
			$("#newsForm").form({
				//加载数据完成后，设置新闻内容
				'onLoadSuccess': function(data){
					 setContent(data.content);
				}
				
			});
			$("#newsForm").form('load', '../news/getNewsById.do?mainId='+mainId);
		}
		
		
		//提交
		function submitForm(state){
			var content = ue.getContent();
			var mainId = $("#mainid").val();
			url = "../news/addNews.do";
			if(mainId){
				url = "../news/updateNews.do";
			}
			var newstype = $("#newstype").combobox("getValue");
			var newSrc = $("#newstype").combobox("getText");
			$.ajax({
				url:url,
				type:"POST",
				cache:false,
				data:{
					content:content,
					title:$("#title").val(),
					userName:$("#userName").val(),
					mainId:mainId,
					state:state,
					newstype:newstype,
					newSrc:newSrc
				},
				success:function(data){
					if(data){
						alert("保存成功");
					}
					$("#newsWin").window('close');
					doSearch();
				},
				error:function(error){
				}
			});
		}
		
		//加载内容
		function setContent(content, isAppendTo) {
	        ue.setContent(content , isAppendTo);
	    }
	</script>
</head>
<body>
	<h2>新闻管理</h2>
	<div class="demo-info" style="margin-bottom:10px">
	</div>
	<div id="searchTool" style="padding:3px">
		<form id="searchForm" >
			<span>新闻标题:</span>
			<input name="title" style="line-height:26px;border:1px solid #ccc">
			<span>新闻类型:</span>
			<select name="newsType" data-options="editable:false" class="easyui-combobox"  panelHeight="auto"  style="width:100px">
			    <option value="">请选择</option>
				<option value="1">娱乐</option>
				<option value="2">财经</option>
				<option value="3">房地产</option>
				<option value="4">自媒体</option>
			</select>
			<a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
		</form>
		<div style="margin-bottom:5px">
			<a href="javascript:void(0);" onclick="editNews()" class="easyui-linkbutton" iconCls="icon-add" >新增新闻</a>
		</div>
	</div>
	<table id="fmDataGrid" class="easyui-datagrid" title="新闻列表" style="width:100%;height:80%;"
			data-options="rownumbers:true,singleSelect:true,url:'../news/getNewsList.do',
				pagination:true,method:'post',toolbar:searchTool,pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'title',width:200,align:'center'">新闻标题</th>
				<th data-options="field:'newsrc',width:100,align:'center'">新闻类型</th>
				<th data-options="field:'userName',width:120,align:'center'">作者</th>
				<th data-options="field:'createdate',width:200,align:'center',sortable:true">创建时间</th>
				<th data-options="field:'zcount',width:90,align:'center',sortable:true">点赞次数</th>
				<th field="action" width="100" align="center" formatter="formatAction">操作</th>
			</tr>
		</thead>
	</table>
	
	<div id="newsWin" class="easyui-window" title="新闻" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:1000px;height:500px;padding:0;margin:0;">
		<div class="easyui-panel"  style="width:100%;height:100%;" >
		<div  align="center" style="padding:10px 0;width:99%;height:97%;">
		    <form id="newsForm" method="post">
		    	<input type="hidden" id="mainid" name="mainid" /> 
		    	<table cellpadding="5" >
		    		<tr>
		    			<td class="" norwap>新闻标题:</td>
		    			<td align="left" >
		    				<input class="easyui-textbox inputText" style="width:700px;" type="text" name="title" id="title" data-options="required:true" />
	    				</td>
		    		</tr>
		    		<tr>
		    			<td class="titlespan" norwap>新闻作者:</td>
		    			<td align="left" >
		    				<input class="easyui-textbox inputText" style="width:700px;" type="text" name="userName" id="userName" data-options="required:true" />
	    				</td>
		    		</tr>
		    		<tr>
		    			<td class="titlespan" norwap>新闻类型:</td>
		    			<td align="left" >
		    				<select name="newstype" id="newstype"  data-options="editable:false,required:true" class="easyui-combobox"  panelHeight="auto"  style="width:200px">
								<option value="1">娱乐</option>
								<option value="2">财经</option>
								<option value="3">房地产</option>
								<option value="4">自媒体</option>
							</select>
	    				</td>
		    		</tr>
		    		<tr>
		    			<td class="titlespan" norwap>新闻内容:</td>
		    			<td>
		    				<div>
			    				<script id="editor" type="text/plain" style="width:700px;height:450px;"></script>
		    				</div>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		    <div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitForm(0)">保存</a>
		    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitForm(1)">发布</a>
		    </div>
		    </div>
		</div>
	</div>
</body>
</html>