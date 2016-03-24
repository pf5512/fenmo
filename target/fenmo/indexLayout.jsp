<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="baseJs/ligerUI/skins/Aqua/css/ligerui-all.css"rel="stylesheet" type="text/css" />
<script src="baseJs/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="baseJs/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="baseJs/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script src="baseJs/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<script src="baseJs/ligerUI/js/plugins/ligerTab.js"></script>
<script src="baseJs/jquery.cookie.js"></script>
<script src="baseJs/json2.js"></script>
<script src="indexdata.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
           //布局
			$("#layout1").ligerLayout({
				leftWidth:200, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged 
			});
			var height = $(".l-layout-center").height();
		    var tabItems = [];
		    var accordion = null;
			$("#framecenter").ligerTab({
                    height: height,
                    showSwitchInTab : true,
                    showSwitch: true,
                    onAfterAddTabItem: function (tabdata){
                        tabItems.push(tabdata);
                        saveTabStatus();
                    },
                    onAfterRemoveTabItem: function (tabid){ 
                        for (var i = 0; i < tabItems.length; i++)
                        {
                            var o = tabItems[i];
                            if (o.tabid == tabid)
                            {
                                tabItems.splice(i, 1);
                                saveTabStatus();
                                break;
                            }
                        }
                    },
                    onReload: function (tabdata){
                        var tabid = tabdata.tabid;
                        addFrameSkinLink(tabid);
                    }
            });
            var memeber = 'baseJs/ligerUI/skins/icons/memeber.gif';
            var myaccount = 'baseJs/ligerUI/skins/icons/myaccount.gif';
            $("#left").ligerTree({
                data: [
	                { dataindex:0,text: '群组管理', icon: myaccount },
	                { dataindex:1,text: '用户管理', icon: memeber },
	                { dataindex:2,text: '新闻管理', icon: memeber }
                ]
            });
            var tab = liger.get("framecenter");
            accordion = liger.get("accordion1");
            $("#left").ligerTree({
                    data : indexdata,
                    checkbox: false,
                    slide: false,
                    nodeWidth: 120,
                    attribute: ['nodename', 'url'],
                    render : function(a){
                        if (!a.isnew) return a.text;
                        return '<a href="' + a.url + '" target="_blank">' + a.text + '</a>';
                    },
                    onSelect: function (node){
                        if (!node.data.url) return;
                        if (node.data.isnew)
                        { 
                            return;
                        }
                        var tabid = $(node.target).attr("tabid");
                        if (!tabid){
                            tabid = new Date().getTime();
                            $(node.target).attr("tabid", tabid)
                        } 
                        f_addTab(tabid,node.data.text, node.data.url);
                    }
              });
              function f_addTab(tabid, text, url){
                tab.addTabItem({
                    tabid: tabid,
                    text: text,
                    url: url,
                    callback: function (){
                      addFrameSkinLink(tabid); 
                    }
                });
             }
             function f_heightChanged(options)
             {  
                if (tab)
                    tab.addHeight(options.diff);
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
             }
             function addFrameSkinLink(tabid){
                var prevHref = getLinkPrevHref(tabid) || "";
                var skin = getQueryString("skin");
                if (!skin) return;
                skin = skin.toLowerCase();
                attachLinkToFrame(tabid, prevHref + skin_links[skin]);
            }
            function attachLinkToFrame(iframeId, filename)
            { 
                if(!window.frames[iframeId]) return;
                var head = window.frames[iframeId].document.getElementsByTagName('head').item(0);
                var fileref = window.frames[iframeId].document.createElement("link");
                if (!fileref) return;
                fileref.setAttribute("rel", "stylesheet");
                fileref.setAttribute("type", "text/css");
                fileref.setAttribute("href", filename);
                head.appendChild(fileref);
            }
            function getLinkPrevHref(iframeId){
                if (!window.frames[iframeId]) return;
                var head = window.frames[iframeId].document.getElementsByTagName('head').item(0);
                var links = $("link:first", head);
                for (var i = 0; links[i]; i++)
                {
                    var href = $(links[i]).attr("href");
                    if (href && href.toLowerCase().indexOf("ligerui") > 0)
                    {
                        return href.substring(0, href.toLowerCase().indexOf("lib") );
                    }
                }
            }
            function saveTabStatus(){ 
              $.cookie('liger-home-tab', JSON2.stringify(tabItems));
            }
            function getQueryString(name){
                var now_url = document.location.search.slice(1), q_array = now_url.split('&');
                for (var i = 0; i < q_array.length; i++)
                {
                    var v_array = q_array[i].split('=');
                    if (v_array[0] == name)
                    {
                        return v_array[1];
                    }
                }
                return false;
            }
	});
</script>
<style type="text/css">
body {
	padding: 5px;
	margin: 0;
	padding-bottom: 15px;
}
#layout1 {
	width: 100%;
	margin: 0;
	padding: 0;
}
.l-page-top {
	height: 30px;
	background: #f8f8f8;
	margin-bottom: 3px;
}
h4 {
	margin: 20px;
}
</style>
</head>
<body style="padding:10px">
	<div class="l-page-top">这个不是layout的一部分，作为单独的页面的头部</div>
	<div id="layout1">
		<div position="left" id="left"></div>
		<div position="center" id="framecenter"> 
            <div tabid="home" title="我的主页" style="height:600px" >
                <iframe frameborder="0" name="home" id="home" src=""></iframe>
            </div> 
        </div> 
	</div>
</body>
</html>
