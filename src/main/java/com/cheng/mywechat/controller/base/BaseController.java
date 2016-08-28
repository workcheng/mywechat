package com.cheng.mywechat.controller.base;

import com.cheng.mywechat.comm.logger.ZeroLogger;
import com.cheng.mywechat.comm.logger.ZeroLoggerFactory;
import com.cheng.mywechat.comm.properties.ZGProperties;
import com.cheng.mywechat.comm.redis.ZGRedisTemplete;
import com.cheng.mywechat.service.test.TestService;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/base")
public class BaseController {
  private static final ZeroLogger log = ZeroLoggerFactory.getLogger(BaseController.class);
  @Autowired
  private ZGRedisTemplete zgRedisTemplete;
  @Autowired
  private WxMpService     wxMpService;
  @Autowired
  private TestService     testService;

  @RequestMapping(value = "/tes")
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
      // WxMpUser user = (WxMpUser) zgRedisTemplete.getHash(key,"WxMpUser",1);
      String message = (String) zgRedisTemplete.getValue(key);
      if (StringUtils.isNotBlank(message)) {
        WxMpUser user = wxMpService.userInfo(key, "zh_CN");
        String subTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(1000 * user.getSubscribeTime());
        String body = MessageFormat.format(ZGProperties.get("config/textConfig/test.properties",
            "hello.body"), user.getNickname(), user.getSex(), user.getCity(), user.getProvince(), user.getCountry(),
            user.getHeadImgUrl(), subTime, zgRedisTemplete.getExpire(key) + "秒",message);
        return body;
      } else {
        return "已失效";
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  @RequestMapping("/testing/mybatis")
  @ResponseBody
  public Object giveMeAUser(){
    try {
      return testService.getTest();
    } catch (SQLException e) {
      log.error("errmsg",e);
      return null;
    }
    
  }

}
