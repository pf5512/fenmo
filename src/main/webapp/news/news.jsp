<%@page pageEncoding="utf-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>新闻</title>
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
		.inputText{
			width:400px;
		}
		.titlespan{
			width:120px;
		}
	</style>
	<%
		String mainId = request.getParameter("mainId");
	 %>
	<script type="text/javascript">
	
		//创建富文本框
		var ue = UE.getEditor('editor',{
			elementPathEnabled:false //不显示元素路径
			//UEDITOR_HOME_URL:window.location.protocol+"//"+window.location.host+"/fenmo/plugin/ueditor" 指定根目录
		});
	
		var mainId = '<%=mainId%>';
		
		ue.ready(function() {
			if(mainId){
				loadRemote();
			}
        });
		
		//加载数据
		function loadRemote(){
			$("#newsForm").form({
				//加载数据完成后，设置新闻内容
				'onLoadSuccess': function(data){
					 setContent(data.content);
				}
				
			});
			$("#newsForm").form('load', '/fenmo/news/getNewsById.do?mainId='+mainId);
		}
		
		
		//提交
		function submitForm(){
			var content = ue.getContent();
			url = "/fenmo/news/addNews.do";
			if(mainId){
				url = "/fenmo/news/updateNews.do";
			}
			$.ajax({
				url:url,
				type:"POST",
				data:{
					content:content,
					title:$("#title").val(),
					userName:$("#userName").val(),
					mainId:mainId
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
		
		//加载内容
		function setContent(content, isAppendTo) {
	        ue.setContent(content , isAppendTo);
	    }
	</script>
</head>
<body>
	<div class="easyui-panel" title="新闻" style="width:1000px;" >
		<div  align="center" style="padding:10px 0;">
	    <form id="newsForm" method="post">
	    	<table cellpadding="5" >
	    		<tr>
	    			<td class="titlespan" >新闻标题:</td>
	    			<td align="left" >
	    				<input class="easyui-textbox inputText"  type="text" name="title" id="title" data-options="required:true" />
    				</td>
	    		</tr>
	    		<tr>
	    			<td class="titlespan" >新闻作者:</td>
	    			<td align="left" >
	    				<input class="easyui-textbox inputText" type="text" name="userName" id="userName" data-options="required:true" />
    				</td>
	    		</tr>
	    		<tr>
	    			<td class="titlespan" >新闻内容:</td>
	    			<td>
	    				<div>
		    				<script id="editor" type="text/plain" style="width:800px;height:500px;"></script>
	    				</div>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitForm()">保存</a>
	    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="fabuForm()">发布</a>
	    </div>
	    </div>
	</div>
</body>
</html>