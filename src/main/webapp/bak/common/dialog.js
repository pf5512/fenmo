/**
*对话框
*js主要包括 弹出和关闭对话框
*信息初始化等
*时间：2012-09-20
*作者：ray qq:236833129
**/
//全局对话框变量
var window_dialog;
var window_dialog1;

/**打开窗口*/
function showDialog(params,top){	
	var heightDiff = 0;  
	//默认最外层弹出
	var  topWinow = window.top;
	if(top)topWinow = top;
	var param =$.extend({
		width: $(topWinow).width()*0.75,
		height: ($(topWinow).height()-heightDiff)*0.8,
		top:($(topWinow).height()-heightDiff)*0.1,
		left:null,
		isResize: false,
		name:false,
		url: false,
		showMax: true,                             //是否显示最大化按钮 
	    showToggle: false, 
	    showMin:false,
	    isHidden:true,
        title:'',
        callBack:false,
        buttons:false,
        openDialog:false
},params);
	//是不是每次都打开新的窗口
	if(param.openDialog){
		window_dialog1 =topWinow.$.ligerDialog.open({
			width: param.width,
			height: param.height,
			isResize: param.isResize,
			name:param.name,
			url: param.url,
			top:param.top,
			left:param.left,
            title:param.title,
            showMax: param.showMax,                             //是否显示最大化按钮 
            showMin: param.showMin,                         
            showToggle: param.showToggle, 
            isHidden: false, 
            buttons:param.buttons
		});
	}else{
		if(!window_dialog){
			window_dialog=topWinow.$.ligerDialog.open({
				width: param.width,
				height: param.height,
				isResize: param.isResize,
				name:param.name,
				url: param.url,
				top:param.top,
	            title:param.title,
	            showMax: param.showMax,                             //是否显示最大化按钮 
	            showMin: param.showMin,                         
	            showToggle: param.showToggle, 
	            isHidden: param.isHidden, 
	            buttons:param.buttons
			});
		}else{
			//回调函数
			if(param.callBack)
				param.callBack();
			//打开心路径
			if(param.url)
		    	window_dialog.setUrl(param.url);
			//显示窗口
			window_dialog.show();
		}
	}
	
}
/**
 * 关闭对话框
 */
function closeDialog(){
	window_dialog.close();
	window_dialog=null;
};
/**
 * 关闭对话框
 */
function closeOpenDialog(){
	window_dialog1.close();
	window_dialog1=null;
};
/**
 * 隐藏对话框
 */
function hideDialog(){
	window_dialog.hide();
};

/**
 * 描述：新建一个window窗口
 * @param {} parms 参数对象
 * @param {} window 要打开的窗口
 */
function showNewWindow(parms,win){
	var _window=win?win:top.window;
	parms = $.extend({
		url:'about:blank',
		target:'_blank',
		title:"新窗口",
		height:_window.$height,
		width:_window.$width,
		left:0,
		top:0,
		menubar:'yes',
		location:'yes',
		resizable:'yes',
		titlebar:'yes',
		toolbar:'yes'
	},parms);
	
	var _params="";
	if(parms.height)_params+=",height="+parms.height+"px";
	if(parms.width)_params+=",width="+parms.width+"px";
	if(parms.left)_params+=",left="+parms.left;
	if(parms.top)_params+=",top="+parms.top;
	if(parms.menubar)_params+=",menubar="+parms.menubar;
	if(parms.location)_params+=",location="+parms.location;
	if(parms.resizable)_params+=",resizable="+parms.resizable;
	if(parms.titlebar)_params+=",titlebar="+parms.titlebar;
	if(parms.toolbar)_params+=",toolbar="+parms.toolbar;
	var nw = _window.open(parms.url,parms.target,_params.substring(1));
    
	var interval = setInterval(function(){
		if(nw.document){
			nw.document.title = parms.title;
			clearInterval(interval);
		}
	},1000);
	
}



/usr/local/gcc-4.1.2
