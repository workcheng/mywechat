package com.cheng.mywechat.controller.base;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cheng.mywechat.comm.properties.ZGProperties;
import com.cheng.mywechat.comm.redis.ZGRedisTemplete;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
@RequestMapping("/base")
public class BaseController {
  @Autowired
  private ZGRedisTemplete zgRedisTemplete;

  @RequestMapping(value = "/test")
  public @ResponseBody Object test() {
    Map<String, Object> map = new HashMap<>();
    List<String> list = new ArrayList<>();
    list.add("c++");
    list.add("c");
    map.put("relax", list);
    return map;
  }

  @RequestMapping("/test/{key}")
  public @ResponseBody Object mail(HttpServletRequest request, HttpServletResponse response,
      @PathVariable @NotNull String key) {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    try {
      // return ZGProperties.get("config/textConfig/html.property",
      // "mail.register.verify");
//      WxMpUser user = (WxMpUser) zgRedisTemplete.getHash(key,"WxMpUser",1);
      WxMpUser user = (WxMpUser) zgRedisTemplete.getValue(key);
      if (null != user) {
        String subTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(1000*user.getSubscribeTime());
        String body = MessageFormat.format(ZGProperties.get("config/textConfig/test.properties",
            "hello.body"), user.getNickname(), user.getSex(), user.getCity(), user.getProvince(), user.getCountry(),
            user.getHeadImgUrl(), subTime,zgRedisTemplete.getExpire(key)+"秒");
        return body;
      } else {
        return "已失效";
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
