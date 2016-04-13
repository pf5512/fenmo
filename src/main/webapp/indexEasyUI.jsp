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
<script type="text/javascript">
   $(document).ready(function(){
	  $("#news").show();
   });
   
   function newsGl(){
	  $("#mainIframe").attr("src", "news/newsList.jsp");
   }
   
   function roomsGl(){
	  $("#mainIframe").attr("src", "room/roomsList.jsp");
   }
</script>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">north region</div>
	<div data-options="region:'west',split:true,title:'Ŀ¼��'" style="width:150px;padding:10px;">
	  <div class="easyui-panel" >
		<ul class="easyui-tree">
			<li>
				<span>���ݹ���</span>
				<ul>
					<li><a onclick="roomsGl();return false;">Ⱥ�����</a></li>
				    <li><a onclick="newsGl();return false;">���Ź���</a></li>
				    <li>�û�����</li>
				    <li>��̬����</li>
				</ul>
			</li>
		</ul>
	</div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">�ײ�</div>
	<div data-options="region:'center',title:'���'" id = "panel" style="width:100%;height:100%;">
	    <iframe id="mainIframe" src="news/newsList.jsp" style="width:100%;height:100%;"></iframe>
	</div>
</body>
</html>