/**
* jQuery ligerUI 1.2.4
* 
* http://ligerui.com
*  
* Author ray 2014 [ gd_star@163.com ] 
* 
*/
(function ($)
{

    $.fn.ligerSearchBar = function (options)
    { 
        return $.ligerui.run.call(this, "ligerSearchBar", arguments);
    };

    $.fn.ligerGetTabManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetSearchBarManager", arguments);
    };

    $.ligerDefaults.SearchBar = {
        height:30,
        width:'100%',
        namePre:null,
        formName:null,
        items:[]
    };
   

    $.ligerMethos.SearchBar = {};

    $.ligerui.controls.SearchBar = function (element, options)
    {
        $.ligerui.controls.SearchBar.base.constructor.call(this, element, options);
    };
    $.ligerui.controls.SearchBar.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return 'SearchBar';
        },
        __idPrev: function ()
        {
            return 'SearchBar';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.SearchBar;
        },
        _getItem:function(id){
        	 var g = this, p = this.options;
        	 var item = null;
        	 $.each(p.items,function(i,it){
        		 if(it.name == id)item = it;
        	 });
        	 return item;
        },
        _render: function ()
        {
            var g = this, p = this.options;
          //级联
     	   var cascade = [];
           g.searchBar = $(g.element);
           g.searchBar.addClass('l-searchbar');
           if(p.width)g.searchBar.width(p.width);
           if(p.height)g.searchBar.height(p.height);
           g.form = $("<form></form>");
           if(p.formName)g.form.attr("id",p.formName).attr("name",p.formName);
           if(p.items){
        	   
        	   $.each(p.items,function(i,item){
        		   var hid,text,label,it,opt;
        		 
        		   if (item.line || item.type == "line")
                   {
        			   g.form.append('<div class="l-bar-separator"></div>');
                       return;
                   }
                   if (item.type == "text")
                   {
                	   it= $('<div class="l-searchbar-item"></div>');
                	   it.css('height',25);
                	   if(item.label){
                		  label = $('<span>'+item.label+'</span>');
                 		  label.css('width','auto');
                		  label.css('height',25);
                		  label.css('margin-top',2);
                		  it.append(label);
                	  } 
                	  text = $('<span><input type="text" id="bar_'+item.name+'"/></span>');
                	  text.width(item.width||160);
                	  text.find('input').width(item.width||160).attr("id",item.name)
                	  .attr("name",p.namePre?p.namePre+item.name:item.name);
                	  opt = $.extend(item.params,{width:item.width||160});
                	  text.find('input').ligerTextBox(opt);
                	  it.append(text);
                	  g.form.append(it);
                	  g.form.find('span').css("float",'left');
                	  return;
                   }
                   if (item.type == "select")
                   {
                	 //是否有级联 
              		  if(item.cascade){
              			  cascade.push(item);
              		  }
                	   it= $('<div class="l-searchbar-item"></div>');
                	   it.css('height',25);
                	   if(item.label){
                		  label = $('<span>'+item.label+'</span>');
                		  label.css('width','auto');
                		  label.css('height',25);
                		  label.css('margin-top',2);
                		  it.append(label);
                	  } 
                	  text = $('<span><input type="text" id="bar_'+item.name+'"/></span>');
                	  hid = $('<span><input type="hidden" id="hid_bar_'+item.name+'"/></span>');
                	  text.width(item.width||160);
                	  text.find('input').width(item.width||160);
                	  hid.find('input').attr("id",item.name).attr("name",p.namePre?p.namePre+item.name:item.name);
                	  opt = $.extend(item.params,{onSelected:function(data){
                		  hid.find('input').val(data);
                	  },width:item.width||160});
                	  var combo = text.find('input').ligerComboBox(opt);
                	  //默认选中第几条数据
                	  if(item.selectIndex>-1){
                		  if(opt.data&&opt.data.length>0){
                			  var index = 0;
                			  if(item.selectIndex<opt.data.length)index= item.selectIndex;
                			  var field = "id";
                  			 if(item.params&&item.params.valueField)
                  				field = item.params.valueField;
                			  var va = opt.data[index][field];
                			  combo.selectValue(va);
                		  }
                	  }
                	  it.append(text);
                	  it.append(hid);
                	  g.form.append(it);
                	  g.form.find('span').css("float",'left');
                	  return;
                   }
                   if (item.type == "date")
                   {
                	   it= $('<div class="l-searchbar-item"></div>');
                	   it.css('height',25);
                	   if(item.label){
                		  label = $('<span>'+item.label+'</span>');
                		  label.css('width','auto');
                		  label.css('height',25);
                		  label.css('margin-top',2);
                		  it.append(label);
                	  } 
                	  text = $('<span><input type="text" id="bar_'+item.name+'"/></span>');
                	  text.width(item.width||160);
                	  text.find('input').width(item.width||160).attr("id",item.name)
                	  .attr("name",p.namePre?p.namePre+item.name:item.name);
                	  opt = $.extend(item.params,{width:item.width||160});
                	  text.find('input').ligerDateEditor(opt);
                	  it.append(text);
                	  g.form.append(it);
                	  g.form.find('span').css("float",'left');
                	  return;
                   }
                   
                   if (item.type == "popup")
                   {
                	   it= $('<div class="l-searchbar-item"></div>');
                	   if(item.label){
                		  label = $('<span>'+item.label+'</span>');
                		  label.css('width','auto');
                		  it.append(label);
                	  } 
                	  text = $('<span><input type="text" id="bar_'+item.name+'"/></span>');
                	  hid = $('<span><input type="hidden"/></span>');
                	  text.width(item.width||160);
                	  text.find('input').width(item.width||160);
                	  hid.find('input').attr("id",item.name).attr("name",p.namePre?p.namePre+item.name:item.name);
                	  opt = $.extend(item.params,{onSelected:function(data){
                		  hid.find('input').val(data.value);
                	  },width:item.width||160});
                	  text.find('input').ligerPopupEdit(opt);
                	  it.append(text);
                	  it.append(hid);
                	  g.form.append(it);
                	  g.form.find('span').css("float",'left');
                	  return;
                   }
                   
                   if (item.type == "button")
                   {
                	   it= $('<div class="l-searchbar-item"></div>');
                	   opt = $.extend(item.params,{width:item.width||60,text:'查询'});
                	   it.ligerButton(opt);
                	   g.form.append(it);
                	   it.css("float",'right').css("margin-right","6px")
                	   .css("margin-bottom","5px").css("margin-top","2px");
                	  return;
                   }
                   if (item.type == "hidden")
                   {
                	   text = $('<input type="hidden"/>');
                	   text.width(item.width||160).attr("id",item.name)
                 	  .attr("name",p.namePre?p.namePre+item.name:item.name);
                	   g.form.append(text);
                	  return;
                   }
                   if (item.type == "tip")
                   {
                	  it= $('<div class="l-searchbar-item"></div>');
                	  if(item.label){
                		  label = $('<span>&nbsp;&nbsp;<img src="../page/img/tiptitle.jpg" width="10" height="10" />&nbsp;</span><span style="color:Red;font-weight:bold;">'+item.label+'</span>');
                		  label.css('width','auto');
                		  it.append(label);
                	  } 
                	  g.form.append(it);
                	  g.form.find('span').css("float",'left');
                	  return;
                   }
        	   });
           }
          
           //添加按钮
           g.searchBar.append(g.form);
           g.set(p);
           
         //生成级联
    	   $.each(cascade,function(i,it){
    		   opt = $.extend(it.params,{onSelected:function(data){
    			   $("#hid_bar_"+it.name).val(data);
    			   if(it.cascade&&it.cascade.data){
    				   var d = it.cascade.data(data);
    				   liger.get("bar_"+it.cascade.id).setData(d);
    				   var combox = $("#bar_"+it.cascade.id).ligerGetComboBoxManager();
    				   var ite = g._getItem(it.cascade.id);
    				   //默认选中第几条数据
                 	   if(ite.selectIndex>-1){
                 		  if(d&&d.length>0){
                 			  var index = 0;
                 			 if(ite.selectIndex<d.length)index=ite.selectIndex;
                 			 var field = "id";
                 			 if(ite.params&&ite.params.valueField)
                 				field = ite.params.valueField;
                 			 var vl = d[index][field];
		            	      combox.selectValue(vl);
                 		  }
                 	  }
    			   }
         	  },width:it.width||160});
         	  $("#bar_"+it.name).ligerComboBox(opt);
         	 
    	   });
        }
    });

})(jQuery);