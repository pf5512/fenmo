<%@page pageEncoding="GBK" language="java"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="GBK">
	<title>Basic Form - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="./../jquery-easyui-1.4.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./../jquery-easyui-1.4.4/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./../jquery-easyui-1.4.4/demo/demo.css">
	<script type="text/javascript" src="./../jquery-easyui-1.4.4/jquery.min.js"></script>
	<script type="text/javascript" src="./../jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
	
	<script type="text/javascript" charset="gbk" src="../ueditor.config.js"></script>
    <script type="text/javascript" charset="gbk" src="../ueditor.all.js"> </script>
    <!--�����ֶ��������ԣ�������ie����ʱ��Ϊ��������ʧ�ܵ��±༭������ʧ��-->
    <!--������ص������ļ��Ḳ������������Ŀ����ӵ��������ͣ���������������Ŀ�����õ���Ӣ�ģ�������ص����ģ�������������-->
    <script type="text/javascript" charset="gbk" src="../lang/zh-cn/zh-cn.js"></script>

    <style type="text/css">
		.inputText{
			width:400px;
		}
		.titlespan{
			width:120px;
		}
	</style>
	<script type="text/javascript">
	
		//�������ı���
		var ue = UE.getEditor('editor',{
			elementPathEnabled:false //����ʾԪ��·��
		});
		
		//�ύ
		function submitForm(){
			$('#newsForm').form('submit');
		}
		
		//��������
		function setContent(content, isAppendTo) {
	        ue.setContent(content , isAppendTo);
	    }
	    
	    
	    //��ʼ����ϼ�������
	   	ue.ready( function( editor ) {
            setContent('<p><img title="1458733219702024395.jpg" alt="Tulips.jpg" src="/testproject/jsp/upload/image/20160323/1458733219702024395.jpg"/></p>');
        } );
        
	</script>
</head>
<body>
	<div class="easyui-panel" title="����" style="width:1000px;" >
		<div  align="center" style="padding:10px 0;">
	    <form id="newsForm" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td class="titlespan" >���ű���:</td>
	    			<td align="left" >
	    				<input class="easyui-textbox inputText"  type="text" name="name" data-options="required:true" />
    				</td>
	    		</tr>
	    		<tr>
	    			<td class="titlespan" >��������:</td>
	    			<td align="left" >
	    				<input class="easyui-textbox inputText" type="text" name="email" data-options="required:true" />
    				</td>
	    		</tr>
	    		<tr>
	    			<td class="titlespan" >��������:</td>
	    			<td>
	    				<div>
		    				<script id="editor" type="text/plain" style="width:800px;height:500px;"></script>
	    				</div>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitForm()">����</a>
	    </div>
	    </div>
	</div>
</body>
</html>