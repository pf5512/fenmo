package com.cn.fenmo.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cn.fenmo.pojo.News;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.service.NewsService;
import com.cn.fenmo.spider.bean.LinkTypeData;
import com.cn.fenmo.spider.core.ExtractService;
import com.cn.fenmo.spider.rule.Rule;
import com.cn.fenmo.util.CNST;
import com.cn.fenmo.util.NewsCnst;
import com.cn.fenmo.util.StringUtil;

@Component  
public class SampleServiceImpl  implements SampleService {
  @Autowired
  private IUserService userService;
  
  @Autowired
  private NewsService newsService;
 
  // @Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次  
  @Scheduled(cron="0 0 6 * * ?")   //每天上午6点
  public void myTest(){  
    //用户等级
    int starLevel=6;
    List<UserBean> userlist= null;
    if(userService!=null){
      userlist = this.userService.getUsersByStarLevel(starLevel);
    }
    List<LinkTypeData> extracts =  new ArrayList<LinkTypeData>();
    List<News> list =  null;
    for(int i=0;i<userlist.size();i++){
      String nickName = userlist.get(i).getNickname();
      String userName = userlist.get(i).getUsername();
      Rule rule = new Rule("http://news.baidu.com/ns",  
          new String[] { "word","tn","from" }, new String[] {nickName,"news","news"},  
          null, -1, Rule.GET);  
      extracts = ExtractService.extract(rule);  
      for (LinkTypeData data :extracts){
        String title = data.getLinkText();
        String href = data.getLinkHref();
        if(StringUtil.isNull(title)||title.indexOf(nickName)<0||title.indexOf("去网页搜索")>0){
          continue;
        }
        if(StringUtil.isNull(href)){
          continue;
        }
        if(href.indexOf("baidu.com")>0){
          continue;
        }
        if(href.indexOf("http")<0 && href.indexOf("https")<0){
          continue;
        }
        if(this.newsService.selectByUniqueKey(href)!=null){
          continue;
        }else{
          News beanNews = new News();
          beanNews.setTitle(title);
          beanNews.setNewsHttpUrl(href);
          beanNews.setMainid(new Date().getTime()+new Random().nextInt(10000));
          if(href.indexOf("qq")>0){
            beanNews.setNewsrc("腾讯网");
          }else if(href.indexOf("163.com")>0){
            beanNews.setNewsrc("网易网");
          }else if(href.indexOf("sohu.com")>0){
            beanNews.setNewsrc("搜狐网");
          }else if(href.indexOf("sina.com.cn")>0){
            beanNews.setNewsrc("新浪网");
          }else if(href.indexOf("people.com.cn")>0){
            beanNews.setNewsrc("人民网");
          }else if(href.indexOf("ce.cn")>0){
            beanNews.setNewsrc("中国经济网");
          }else{
            beanNews.setNewsrc("未知");
          }
          beanNews.setNewstype(CNST.CIRCLE_HLW);
          beanNews.setState(NewsCnst.PUBLISH);
          beanNews.setUserName(userName);
          beanNews.setCreatedate(new Date());
          beanNews.setNewHeadImgUrl("http://60.190.243.154/18680683004/20160419162858_101.jpg");
          this.newsService.save(beanNews);
        }
      }
//      if(list!=null && list.size()>0){
//        this.newsService.addAatchNews(list);
//      }
    }
  }
  
  
}
