<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"></script>
</head>
<body>
    <h1> 上传用户头像文件</h1> 
 	<form name="userForm" action="/fenmo/user/uploadTx.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
			<input type="file" name="myfile">
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	<h1>动态图片上传</h1> 
 	<form name="userForm" action="/fenmo/dynamic/uploadDtImg.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
			<input type="file" name="myfile">
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	<h1> 发布动态</h1> 
     <form name="userForm" action="/fenmo/dynamic/publishDynamic.do" enctype="multipart/form-data" method="post"">
 		<div id="d">
			<input type="text" name="content">
		</div>
		<br>
	    <div id="newUpload2">
			<input type="file" name="myfile">
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="保存" >
 	</form> 
 	<h1> 发布新闻</h1> 
     <form name="userForm" action="/fenmo/news/publishNews.do" enctype="multipart/form-data" method="post"">
 		<div id="">
	    	标题:<input type="text" name="title"></br>
	                内容:<input type="text" name="content">
		</div>
		<input type=hidden value="15867178325" name="userPhone" />
		<input type="submit" value="保存" >
 	</form> 
     <h1>新闻评论</h1> 
     <form name="userForm" action="/fenmo/news/discuss.do" enctype="multipart/form-data" method="post"">
 		<div id="">
	                内容:<input type="text" name="content">
		</div>
	    <input type=hidden value="1453813956368" name="newsId" />
		<input type=hidden value="15867178325" name="userPhone" />
		<input type="submit" value="保存" >
 	  </form> 
 	<h1>动态评论</h1> 
 	<form name="userForm" action="/fenmo/dynamic/discuss.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
			<input type="text" name="content">
		</div>
		<input type=hidden value="1453559546136" name="dynamicId" />
		<input type=hidden value="15867178324" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	
     <h1>创建群组(公有)</h1> 
     <form name="userForm" action="/fenmo/room/createPublicRoom.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
			类型：<input type="text" name="type"><br>
			群组名称：<input type="text" name="roomName"><br>
			主题：	 <input type=text value="主题" name="subject" />
		        简介：<input type="text" name="desc"><br>
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	 <h1>创建群组(私有)</h1> 
     <form name="userForm" action="/fenmo/room/createPrivateRoom.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
			群组成员：<input type="text" name="members"><br>
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	 </form> 
 	 
 	 <h1>上传用户图片</h1> 
     <form name="userForm" action="/fenmo/user/uploadImg.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
        	图片：<input type="file" name="myfile">
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	 </form> 
 	 <h1>更新用户头像</h1> 
     <form name="userForm" action="/fenmo/user/updateHeadImg.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
        	头像：<input type="file" name="myfile">
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
     <h1>更新群组头像</h1> 
     <form name="userForm" action="/fenmo/room/updateHeadImg.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
        	头像：<input type="file" name="myfile">
		</div>
		<input type=hidden value="165335060101202400" name="groupId"  />
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	  <h1>上传群组头像</h1> 
     <form name="userForm" action="/fenmo/room/uploadHeadImg.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
        	头像：<input type="file" name="myfile">
		</div>
		<input type=hidden value="165335060101202400" name="groupId"  />
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	 <h1>修改群组名称</h1> 
     <form name="userForm" action="/fenmo/room/updateRoomName.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
        	名称：<input type="text" name="roomName">
		</div>
		<input type=hidden value="165335060101202400" name="groupId"  />
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="提交" >
 	</form> 
     <h1>群组加人</h1> 
     <form name="userForm" action="/fenmo/room/addUser.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
        	名称：<input type="text" name="addUserName" value="">
		</div>
		<input type=hidden value="165335060101202400" name="groupId"  />
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="提交" >
 	</form> 
</body>
</html>