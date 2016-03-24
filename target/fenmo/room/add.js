$(document).ready(function(){
//	initDepartTree();
	initPopUpEdit();
	$("#contextform").ligerValidate();
	$("form").ligerForm();
	/**设置用户编号*/
	$("#yhbh").val(setUserId());
});
var regist = false;
/**查询是否已经被注册*/
function selectUser(){
	var yhzh = $("#yhzh").val();
	$.ajax({ 
		type : 'post',
		async : false,
		dataType : 'json',
		url:contextPath + '/system/userAction!getUserByYhzh.do?yhzh='+yhzh,
		success : function(result) {
			if(result.dataList.length == 0){
				regist  = true;
				$("#czyhzh").css("display","none");
			}else{
				regist  = false;
				$("#czyhzh").css("display","block");
			}
		},
		error : function(XMLResponse) {
			alertMSG(TYPE_SYS_ERROR,TYPE_ERROR);
		}
	});
}
/**
 * 权限列表
 */
    var grid;
	var options = {
		data:[
	      {id:'0',text:'派出所操作员'},
	      {id:'1',text:'分局管理员'},
	      {id:'2',text:'市局管理员'},
	      {id:'3',text:'分局观察员'},
	      {id:'4',text:'市局观察员'}
		]	
	};
/**
 * 获取部门所有数据
 */
var deptList = getDeptList();
	
	
	var edit;
	/**
	 * 初始化下拉部门列表
	 */
	function initPopUpEdit(){
		edit = $("#yhbm_1").ligerPopupEdit({
		    grid: getGridOptions(true),
		    nullText:'请选择上级部门',
		    valueField: 'id',
		    textField: 'bmmc',
		    onSelected:function(data){
		    	$("#yhbm").val(data.value);
		    },
		    popWidth:526,//新增 弹出框宽度
	        popHeight:276,//新增 弹出框高度
	        popTitle:'部门数据'
		});
	}
	/**
	 * 加载下拉部门表单
	 * @param checkbox
	 */
	/*function getGridOptions(checkbox) {
	    var options = {
	    		  columns : [
	    		             {display : '部门编号',name : 'bmbh',width:60},
	    		             {display : '部门名称',name : 'bmmc',width:150},
	    		             {display : '上级部门',name : 'sjbmbh',render:function(row){
	    		            	 var html="";
	    		            	 $.each(deptList,function(index,dept){
	    		            		 if(row.sjbmbh ==dept.id)
	    		            			 html = dept.bmmc;
	    		            	 });
	    		            	 return html;
	    		             }}
	    				],
	    			url : contextPath + '/system/deptAction!getRecordList.do', // 必须。
	    			root: "dataList",  // 必须。数据源字段名
	    			sortName: "bmbh", // 必须。默认排序字段，使用DB字段名
	    			sortOrder: "ASC", // 默认排序字段，默认值：ASC
	    			pageSize: 30,  // 初始每页显示记录数，默a认值：10
	    			height:100,
	    			width:80,
	    			heightDiff:-20,
	    			usePager:true,
	                checkbox: false
	    };
	    return options;
	}*/
/**
 * 初始化下拉表单
 */
function initDepartTree(){
	$.ajax({ 
		type : 'post',
		async : false,
		dataType : 'json',
		url:contextPath + '/system/treeAction!getAllDept.do',
		success : function(result) {
			$("#yhbm_1").ligerComboBox({
				width:160,
//				selectBoxWidth:300,
//				selectBoxHeight:160,
				height:25,
				tree:{
					data:eval(result.treedata),
					isExpand:null,
					single: true,
//					delay: true ,
					onClick:function(node){
						$("#yhbm_1").val(node.data.text);
						var dept = eval("("+node.data.value+")");
						$("#yhbm").val(dept.bmbh);
					},
					checkbox:false}
			});
		},
		error : function(XMLResponse) {
			alertMSG(TYPE_SYS_ERROR,TYPE_ERROR);
		}
	});
	
	
//	$("#yhbm_1").ligerPopupEdit({
//	    grid: getGridOptions(true),
//	    nullText:'请选择上级部门',
//	    valueField: 'id',
//	    textField: 'bmmc',
//	    onSelected:function(data){
//	    	$("#yhbm").val(data.value);
//	    },
//	    popWidth:320,//新增 弹出框宽度
//        popHeight:200,//新增 弹出框高度
//        popTitle:'部门数据'
//	});
}

/**
 * 显示上级部门下拉表单
 * @param checkbox
 * @returns
 */
function getGridOptions(checkbox) {
    var grid = {
    		  searchbar : {
    		            namePre:'condition.',
    		       	    formName:'contextform',
    		    		items:[
    		    		       {type:'text',label:'部门编号:',name:'bmbh', width:120},
    		    		       {type:'text',label:'部门名称:',name:'bmmc', width:120},
    		    		       {type:'button',width:40,params:{click:function(){
    		    		    	   reload();
    		    		       }}}
    		    		       ]
    		    	},
    		  columns : [
    		             {display : '部门编号',name : 'bmbh',width:120},
    		             {display : '部门名称',name : 'bmmc',width:198},
    		             {display : '上级部门',name : 'sjbmbh',width:150,render:function(row){
    		            	 var html="";
    		            	 $.each(deptList,function(index,dept){
    		            		 if(row.sjbmbh ==dept.id)
    		            			 html = dept.bmmc;
    		            	 });
    		            	 return html;
    		             }}
    				],
    			url : contextPath + '/system/deptAction!getRecordList.do', // 必须。
    			root: "dataList",  // 必须。数据源字段名
    			sortName: "bmbh", // 必须。默认排序字段，使用DB字段名
    			sortOrder: "ASC", // 默认排序字段，默认值：ASC
    			pageSize: 10,  // 初始每页显示记录数，默认值：10
    			height:100,
    			width:100,
    			heightDiff:-20,
    			usePager:true,
                checkbox: false,
                allowUnSelectRow:false,
                onBeforeCheckRow: function(checked,data,rowid,rowdata){
                	$.ligerDialog.alert("aaaaaaaaaaaaa");
                	if(checked){
                		return false;
                	}
                }
    };   
    return grid;
}
/**
 * 获取所有部门数据
 * @returns 部门JSON对象
 */
function getDeptList(){
	var deptList = {};
	$.ajax({ 
		type : 'post',
		async : false,
		dataType : 'json',
		data : {},
		url : contextPath+"/system/deptAction!getRecordJson.do",
		success : function(result) {
			deptList = result.dataList;
		},
		error : function(XMLResponse) {
			
		}
	});	
	return deptList;
}

/**
 * 保存
 * @param condtion
 */
function saveRecord(parentWindow){
	//表单验证
	if(!$("#contextform").valid()){
		return false;
	}
	if(!regist){
		alert("dadadas");
		return false;
	}
	//用户密码加密
	//$("#yhmm").val(hex_md5($("#yhmm").val()));
//序列化表单
var condtion =	$("#contextform").serializeArray();
	$.ajax({ 
		type : 'post',
		async : false,
		dataType : 'json',
		data:condtion,
		url : contextPath+"/system/userAction!addRecord.do",
		success : function(result) {
			//操作成功
			if(result.message.messageStatus == "0"){
				alertMSG(result.message.messageInfo,TYPE_INFO,function(){
					parentWindow.reload();
					parentWindow.closeOpenDialog();
				});
			}else{//操作失败
				alertMSG(result.message.messageInfo,TYPE_ERROR);
			}	
		},
		error : function(XMLResponse) {
			alertMSG(TYPE_SYS_ERROR,TYPE_ERROR);
		}
	});
}
/**
 * 设置用户编号
 * @returns {String}
 */
function setUserId(){
    var nowTime = new Date();
    var month = nowTime.getMonth() + 1;
    var day = nowTime.getDate();
    var hours = nowTime.getHours();
    var min = nowTime.getMinutes();
    var second=nowTime.getSeconds();
    if(month < 10){
     month = "0" + month;
    }
    if(day < 10){
     day = "0" + day;
    }
    if(hours < 10){
     hours = "0" + hours;
    }
    if(min < 10){
     min = "0" + min;
    }
    var timeStr = nowTime.getFullYear() + month + day + hours + min + second;
    return timeStr;
   }
/**
 * 重新加载
 */

function reload(){
	var condition = $("#contextform").serializeArray();
	grid.setOptions({
		url : contextPath + '/system/deptAction!getRecordList.do', // 必须。
		parms:condition
		});
	grid.loadData(true);
}