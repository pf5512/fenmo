<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.cn.fenmo.util.RoomCnst" %>
<html>
<head>
<link href="../baseJs/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="../baseJs/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="../baseJs/ligerUI/skins/Gray/css/all.css" rel="stylesheet"  type="text/css" />
<script src="../baseJs/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="../baseJs/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="../baseJs/ligerUI/js/plugins/ligerSearchBar.js" type="text/javascript"></script>
<script src="../common/dialog.js" type="text/javascript"></script>
<script src="../baseJs/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="CustomersData.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
	   loadPageData();
	});
	var start=0;
	var limit = 10;
	var name="";
	function loadPageData(){
	  	$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/room/getRoomsOfPageForWeb.do",
			dataType: "json",
			data:{
			   roomName:name,
			   start:start,
			   limit:limit
			},
			success : function(data){
			  initGrid(data);
			}
		});
	}
	var grid;
	function initGrid(data){
	  CustomersData.Rows=data.viewPage.listResult;
	  grid = $("#maingrid").ligerGrid({
			height : '100%',
			columns : [ {
				name : 'groupId',
				hide: true,
				minWidth:0
			},{
				display : '群名称',
				name : 'name',
				align : 'left',
				type:'text',
				minWidth : 140
			}, {
				display : '创建者',
				name : 'userid',
				align : 'left',
				type:'text',
				minWidth : 80
			},{
				display : '最大用户数',
				name : 'maxusers',
				align : 'center',
		        type:'text',
				minWidth :10
			},{
				display : '群类型',
				name : 'ispublicStr',
				minWidth : 140
			},{
				display : '创建时间',
				name : 'createdate',
				type:'text',
				minWidth : 140
			},{
				display : '是否只允许群成员发言',
				name : 'membersonlyStr',
				minWidth : 140
			},{
				display : '群分类',
				name : 'typeStr'
			}],
			data : CustomersData,
			pageSize :data.viewPage.pageLimit,
			rownumbers : true,
			toolbar : {
				items : [ {
					text : '增加',
					click : itemclick,
					icon : 'add',
					name:'add'
				}, {
					line : true
				}, {
					text : '修改',
					click : itemclick,
					icon : 'modify',
					name:'modify'
				}, {
					line : true
				}, {
					text : '删除',
					click : itemclick,
					icon: 'delete',
					name:'delete'
				}, { text: '刷新', click: itemclick, icon: 'refresh',name:'refresh'}]
			}
		});
		 $("#pageloading").hide();
	}
	function deleteRow() {
		g.deleteSelectedRow();
	}
	function search(){
	  name =  $("#roomName").val();
	  loadPageData(name);
	}
	/**
	 * 用户操作-新增、修改、删除、授权、刷新
	 */
	function itemclick(item){
		var str = item.name;
		if(str == 'add'){
			addPageOpen();
		}else if(str == 'modify'){
			updatePageOpen();
		}else if(str == 'delete'){
			deleteRecords();
		}else if(str == 'authorize'){
			authorizePageOpen();
		}else if(str == 'refresh'){
			$("#yhbm").val('');
			$("#yhzh").val('');
			$("#yhxm").val('');
			//刷新
			reload();
		}
	}
	/**
	 * 打开用户新增页面
	 */
	function addPageOpen(){
		showDialog({
	        width: 530,
	        height:400,
	        top:70,
	        title : '新增群组',
	        url:'',
	        showMax :false,
			name:'addiframe',
		    openDialog:true,
			buttons: [ 
		             { text: '新增', onclick: function (item, dialog) { 
		            	 dialog.frame.saveRecord(window);
		             } },
		             { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
		});
	}
</script>
</head>
<body style="overflow-x:hidden; padding:2px;">
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<a class="l-button" style="width:120px;float:left; margin-left:10px; display:none;" onclick="deleteRow()">删除选择的行</a>
	<div class="l-clear"></div>
    <!-- 搜索 -->
	<div id="searchbar">
	    群组名称：<input id="roomName" type="text" name="name"/>
	           <input id="btnOK" type="button" value = "搜索" onclick="search()" />
	</div>	
    <div id="maingrid"></div>
	<div style="display:none;"></div>
</body>
</html>
