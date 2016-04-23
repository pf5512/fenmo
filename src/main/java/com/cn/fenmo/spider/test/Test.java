package com.cn.fenmo.spider.test;

import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.cn.fenmo.spider.bean.LinkTypeData;
import com.cn.fenmo.spider.core.ExtractService;
import com.cn.fenmo.spider.rule.Rule;
import com.sun.org.apache.bcel.internal.generic.NEW;


public class Test{
	@org.junit.Test
	public void getDatasByClass(){
		Rule rule = new Rule("http://news.baidu.com",
		    new String[]{ "word" }, new String[] { "柳岩"},"cont_right", Rule.CLASS, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

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
	
	//http://www.chinaso.com/search/pagesearch.htm?q=%E5%A5%A5%E5%B7%B4%E9%A9%AC&site=cankaoxiaoxi.com
	
	@org.junit.Test  
	public void getDatasByCssQueryUserBaidu()  
	{  
	    Rule rule = new Rule("http://news.baidu.com/ns",  
	            new String[] { "word","tn","from" }, new String[] {"刘德华","news","news"},  
	            null, -1, Rule.GET);  
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	    printf(extracts);  
	} 
	
	public static void main(String[] args) throws Exception {
	  String urlString = "Vo%2FEqDfRVOt641%2BhG5GhCD7Zv22TEzQBGq9%2BKDSefamngZSqaggbrasCWCMtYpuQ%2Bwv9rfciDzJZv6q2zBasOg%3D%3D";
	  urlString = URLDecoder.decode(urlString, "utf-8");
	  System.out.println(urlString);
  }
}
