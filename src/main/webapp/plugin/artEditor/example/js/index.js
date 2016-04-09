$(function() {
	"use strict";

	var p='<p>请输入文章正文内容</p>';

	$('#content').artEditor({
		imgTar: '#imageUpload',
		limitSize: 5,   // 兆
		showServer: true,
		uploadUrl: 'news/uploadimage.do',//这里配置服务器图片上传的路径。
		data: {},
		uploadField: 'image',
		placeholader: p,
		validHtml: ["br"],
		uploadSuccess: function(res) {

			var rs=JSON.parse(res);

			if(rs.path){
				return rs.path;

			}
			// return img url
			//这里return 服务器上传后的图片路径。
		},
		uploadError: function(res) {
			alert("抱歉，图片上传失败，请重新上传！");


		}
	});

	$("#publish").click(function(){


	var str=$('#content').getValue();

	var title=$('#title').val();

	if (str==p||str.trim().length==0) {
		alert("请输入文章内容");
		return ;
	}

	if (title.trim().length==0) {
		alert("请输入文章标题");
		return ;
	}

	//这里使用jQuery将获取到的自媒体提交到服务器上。content 为文章正文，title 为文章标题
    alert("str="+str);
	});
});
