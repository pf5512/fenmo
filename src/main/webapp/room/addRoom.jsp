<%@page pageEncoding="utf-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>群组</title>
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
	<script type="text/javascript">
	
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
	    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitForm(0)">保存</a>
	    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitForm(1)">发布</a>
	    </div>
	    </div>
	</div>
</body>
</html>