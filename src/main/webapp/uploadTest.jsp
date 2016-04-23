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
   <h1>修改好友备注</h1> 
     <form name="userForm" action="/fenmo/friend/updateNick.do" enctype="multipart/form-data" method="post">
		friendPhone:<input type=text  name="friendPhone" />
		userPhone:<input type=text  name="userPhone" />
		<div id="newUpload2">
        	备注：<input type="text" name="nickName">
		</div>
		<input type="submit" value="提交" >
 	</form> 
 	   <h1>获取我的好友</h1> 
     <form name="userForm" action="/fenmo/friend/getMyFriend.do" enctype="multipart/form-data" method="post">
		userPhone:<input type=text  name="userPhone" />
			searchKey:<input type=text  name="searchKey" />
		<input type="submit" value="提交" >
 	</form> 
 	 <h1>用户登陆 </h1> 
 	<form name="userForm" action="/fenmo/user/login.do" enctype="multipart/form-data" method="post">
 		<div id="newUpload3">
 		       用户名<input type="text"  name="userPhone"/></br>
			密码：<input type="password" name="passWord" >
		</div>
		<input type="submit" value="登录" >
 	</form> 
    <h1>用户注册 </h1> 
 	<form name="userForm" action="/fenmo/user/userReg.do" enctype="multipart/form-data" method="post">
 		<div id="newUpload3">
 		       用户名<input type="text"  name="userPhone"/></br>
			密码：<input type="password" name="passWord" >
			性别:<input type="radio" name ="sex" value="0"> 男 <input type="radio" name ="sex" value="1"> 女
		</div>
		<input type="submit" value="登录" >
 	</form> 
    <h1>更新用户位置 </h1> 
 	<form name="userForm" action="/fenmo/user/updateLocation.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
 		       用户名<input type="text" value="15867178340" name="userPhone" /></br>
			lat：<input type="text" name="lat" >
		    lng：<input type="text" name="lng" >
		</div>
		<input type="submit" value="登录" >
 	</form>
 	<h1>更新用户资料 </h1> 
 	<form name="userForm" action="/fenmo/user/changeUser.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
 		       用户名<input type="text"  name="userPhone" /></br>
			生日：<input type="text" name="birthday" >
		</div>
		<input type="submit" value="登录" >
 	</form>  
 	<h1>更新群组成员昵称</h1 > 
 	<form name="userForm" action="/fenmo/room/updateUserRemarkInRoom.do" enctype="multipart/form-data" method="post"">
		<input type=hidden value="15867178340" name="userPhone" />
		<input type=hidden value="157568635261419980" name="groupId" />
	    <input type=text value="15867178340" name="逍客" />
		<input type="submit" value="上传" >
 	</form> 
 	<h1>上传用户所在群组的背景图片</h1> 
 	<form name="userForm" action="/fenmo/room/uploadBjImg.do" enctype="multipart/form-data" method="post">
 		<div id="newUpload2">
			<input type="file" name="myfile">
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type=hidden value="1450338266795" name="groupId" />
		<input type="submit" value="上传" >
 	</form> 
 	
 	<h1>获取群组（带背景图片）</h1> 
 	<form name="userForm" action="/fenmo/room/getRoomByUserPhone.do" enctype="multipart/form-data" method="get">
		<input type="text" value="15867178340" name="userPhone" />
		<input type="text" value="157469307008188900" name="groupId" />
		<input type="submit" value="get" >
 	</form> 
    <h1>上传用户头像文件</h1> 
 	<form name="userForm" action="/fenmo/user/uploadImg.do" enctype="multipart/form-data" method="post"">
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
 	 <h1>发布动态</h1> 
     <form name="userForm" action="/fenmo/dynamic/publishDynamic.do"  method="post">
 		<div id="d">
			<input type="text" name="content">
		</div>
		<br>
	    <div id="newUpload2">
			<input type="text" name="imgUrl">
		</div>
		<input type=hidden value="15867178320" name="userPhone" />
		<input type="submit" value="保存" >
 	</form> 
 	 <h1>对动态进行评论</h1> 
     <form name="userForm" action="/fenmo/dynamic/discuss.do"  method="post"">
	        内容：<input type="text" name="content">
		动态Id:<input type="text" name="dynamicId">
		评论者：<input type="text" name="userPhone" />
		<input type="submit" value="评论" >
 	 </form> 
 	 <h1>上传新闻图片</h1> 
     <form name="userForm" action="/fenmo/news/uploadNewsImg.do" enctype="multipart/form-data" method="post"">
 		<div id="er">
	               文件:<input type="file" name="myfiles">
		</div>
		<input type=hidden value="15867178340" name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	<h1>发布新闻</h1> 
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
 	<h1>获取某个动态的评论</h1> 
 	<form name="userForm" action="/fenmo/dynamic/getDyCommentPage.do" enctype="multipart/form-data" method="get">
		start:<input type="text" name="start" />
	    limit:<input type="text"  name="limit" />
	    dynamicId:<input type="text"  name="dynamicId" />
		userPhone:<input type="text" name="userPhone" />
		<input type="submit" value="获取" >
 	</form> 
 	<h1>获取新闻的评论</h1> 
 	<form name="userForm" action="/fenmo/news/getNewsCommentPage.do" enctype="multipart/form-data" method="get">
		start:<input type="text" name="start" />
	    limit:<input type="text"  name="limit" />
	    newsId:<input type="text"  name="newsId" />
		<input type="submit" value="获取" >
 	</form> 
 	
 	<h1>获取周边动态</h1> 
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
		<input type=hidden value="15867171000" name="userPhone" />
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
		groupId:<input type="text"  name="groupId"  />
		userPhone:<input type="text"  name="userPhone" />
		<input type="submit" value="上传" >
 	</form> 
 	 <h1>修改群组名称</h1> 
     <form name="userForm" action="/fenmo/room/updateRoomName.do" enctype="multipart/form-data" method="post"">
 		<div id="newUpload2">
        	名称：<input type="text" name="roomName">
		</div>
		groupId:<input type=text  name="groupId"  />
		userPhone:<input type=text  name="userPhone" />
		<input type="submit" value="提交" >
 	</form> 
 	
     <h1>获取群组成员</h1> 
     <form name="userForm" action="/fenmo/room/getRoomMembersFromLocal.do" enctype="multipart/form-data" method="get">
		groupId:<input type="text" name="groupId"  />
		搜索关键字:<input type="text" name="searchKey"  />
		<input type="submit" value="提交" >
 	</form> 
 	
 	 <h1>群组加多个人:批量加入</h1> 
     <form name="userForm" action="/fenmo/room/addUsers.do" enctype="multipart/form-data" method="post"">
		groupId:<input type=text name="groupId"/>
		members:<input type=text  name="members"/>
	    userPhone:<input type=text  name="userPhone"/>
		<input type="submit" value="提交" >
 	</form> 
     <h1>退出群组</h1> 
     <form name="userForm" action="/fenmo/room/exitFromGroup.do" enctype="multipart/form-data" method="post">
		groupId:<input type=text name="groupId"/>
	    userPhone:<input type=text  name="userPhone"/>
		<input type="submit" value="提交" >
 	</form>
 	 <h1>发送好友请求</h1> 
     <form name="userForm" action="/fenmo/friend/addFriend.do" enctype="multipart/form-data" method="post">
		friendPhone:<input type=text  name="friendPhone"/>
	    userPhone:<input type=text  name="userPhone"/>
		<input type="submit" value="提交" >
 	</form> 
 	
     <h1>群组加人</h1> 
     <form name="userForm" action="/fenmo/room/addUser.do" enctype="multipart/form-data" method="post"">
		groupId:<input type=text name="groupId"  />
		邀请者:<input type=text  name="userPhone" />
	        被邀请者:<input type=text  name="addUserName" />
		<input type="submit" value="提交" >
 	</form> 
</body>
</html>