package com.cn.fenmo.spider.test;

import java.util.List;

import com.cn.fenmo.spider.bean.LinkTypeData;
import com.cn.fenmo.spider.core.ExtractService;
import com.cn.fenmo.spider.rule.Rule;


public class Test{
//	@org.junit.Test
//	public void getDatasByClass(){
//		Rule rule = new Rule("http://news.baidu.com",
//		    new String[]{ "word" }, new String[] { "柳岩"},"cont_right", Rule.CLASS, Rule.GET);
//		List<LinkTypeData> extracts = ExtractService.extract(rule);
//		printf(extracts);
//	}

	@org.junit.Test
	public void getDatasByCssQuery()
	{
		Rule rule = new Rule("http://www.11315.com/search",
				new String[] { "name" }, new String[] { "兴网" },
				"div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	public void printf(List<LinkTypeData> datas){
		for (LinkTypeData data : datas){
			System.out.println(data.getLinkText());
			System.out.println("***********************************");
			System.out.println(data.getLinkHref());
			System.out.println("***********************************");
		}
	}
	
	@org.junit.Test  
	public void getDatasByCssQueryUserBaidu()  
	{  
	    Rule rule = new Rule("http://ent.qq.com/",  
	            new String[] { "word" }, new String[] {"柳岩"},  
	            null, -1, Rule.GET);  
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	    printf(extracts);  
	} 
}
