package com.cn.fenmo.controller;

import java.util.List;

import javax.ws.rs.FormParam;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.fenmo.gt.PushServiceImp;
import com.cn.fenmo.gt.push.PushObject;
import com.cn.fenmo.pojo.Result;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@Controller
@RequestMapping("/push")
public class PushController {

  public static PushServiceImp PushServiceImp = new PushServiceImp();

  @RequestMapping("/pushMessage")
  public @ResponseBody Result<String> pushMessage(@FormParam(value = "title") String title,// 推送要显示的title
      @FormParam(value = "message") String message,// 推送要显示的消息
      @FormParam(value = "transmissionMessage") String transmissionMessage,// 透传消息内容
      @FormDataParam(value = "pushby") int pushby,// 根据什么来推送，alias.tags 等
      @FormDataParam(value = "pushtype") int pushtype,// 推送类型，穿透还是不穿透，要不要提示
      @FormDataParam(value = "receivers") String receivers,// json List<String>// 不管是一个还是多个。
      @FormParam(value = "pushCommand") int command// 推送特殊命令，预留作用。
  ) {
      Gson gson = new Gson();
      List<String> receList = gson.fromJson(receivers,
          new TypeToken<List<String>>() {
          }.getType());
  
      if (receList == null || receList.size() == 0) {
        return new Result<String>("receivers 是空的", "推送接受者不能为空", 200);
      }
      PushObject pushObject = new PushObject(title, message, transmissionMessage,receList, pushby, pushtype);
      PushServiceImp.push(pushObject);
      return new Result<String>("推送成功", "推送成功", 200);


  }
}
