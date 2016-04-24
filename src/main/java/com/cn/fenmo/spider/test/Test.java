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
		Rule rule = new Rule("http://www.fansmo.com.cn/",
				new String[] { "name" }, new String[] { "王健林" },
				"div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	public void printf(List<LinkTypeData> datas){
		for (LinkTypeData data : datas){
			System.out.println("text="+data.getLinkText());
			System.out.println("***********************************");
			
			System.out.println("href="+data.getLinkHref());
			System.out.println("***********************************");
		}
	}
	
	//http://www.chinaso.com/search/pagesearch.htm?q=%E5%A5%A5%E5%B7%B4%E9%A9%AC&site=cankaoxiaoxi.com
	
	//http://www.fansmo.com.cn/plus/search.php?s=9413525167638165204&q=%CD%F5%BD%A1%C1%D6
	
	@org.junit.Test  
	public void getDatasByCssQueryUserBaidu()  
	{  
	    Rule rule = new Rule("http://news.baidu.com/ns",  
	            new String[] { "word","tn","from" }, new String[] {"汤唯","news","news"},  
	            null, -1, Rule.GET);  
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	    printf(extracts);  
	} 
	
	
	 @org.junit.Test  
   public void getDatasByCssQueryForFenMo()  
   {  
      Rule rule = new Rule("http://www.fansmo.com.cn/plus/search.php",  
              new String[] { "s","q"}, new String[] {"9413525167638165204","王健林"},  
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
