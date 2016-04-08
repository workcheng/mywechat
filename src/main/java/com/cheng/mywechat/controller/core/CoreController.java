package com.cheng.mywechat.controller.core;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cheng.mywechat.comm.logger.ZeroLogger;
import com.cheng.mywechat.comm.logger.ZeroLoggerFactory;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@RequestMapping("/core")
@Controller
public class CoreController {
  private static final ZeroLogger log = ZeroLoggerFactory.getLogger(CoreController.class);
  @Autowired
  WxMpServiceImpl                 wxMpService;
  protected WxMpMessageRouter     wxMpMessageRouter;
  @Autowired
  protected WxMpConfigStorage     wxMpConfigStorage;

  @RequestMapping("")
  public void wechat(HttpServletRequest request, HttpServletResponse response) {
    try {
      init();
      service(request, response);
    } catch (ServletException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void init() throws ServletException {

    WxMpMessageHandler test = test();
    WxMpMessageHandler fun = fun();
    WxMpMessageHandler reply = reply();

    // ==============================

    wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
    wxMpMessageRouter

    .rule().async(false).content("andy").handler(test).end()
    .rule().async(false).content("fun").handler(fun).end()

    .rule().async(false).handler(reply).end()
    ;
  }

  private void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    String signature = request.getParameter("signature");
    String nonce = request.getParameter("nonce");
    String timestamp = request.getParameter("timestamp");

    if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
      // 消息签名不正确，说明不是公众平台发过来的消息
      response.getWriter().println("非法请求警告");
      return;
    }

    String echostr = request.getParameter("echostr");
    if (StringUtils.isNotBlank(echostr)) {
      // 说明是一个仅仅用来验证的请求，回显echostr
      response.getWriter().println(echostr);
      return;
    }

    String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw"
        : request.getParameter("encrypt_type");

    if ("raw".equals(encryptType)) {
      // 明文传输的消息
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
      WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
//      if (null != outMessage) {
        response.getWriter().write(outMessage.toXml());
//      }
      return;
    }

    if ("aes".equals(encryptType)) {
      // 是aes加密的消息
      String msgSignature = request.getParameter("msg_signature");
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage,
          timestamp, nonce, msgSignature);
      WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
      if (null != outMessage) {
        response.getWriter().write(outMessage.toEncryptedXml(wxMpConfigStorage));
      }
      return;
    }

    response.getWriter().println("不可识别的加密类型");
    return;
  }

  private WxMpMessageHandler test() {
    WxMpMessageHandler test = new WxMpMessageHandler() {
      public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
          WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("andy").fromUser(wxMessage.getToUserName())
            .toUser(wxMessage.getFromUserName()).build();
        return m;
      }
    };

    return test;
  }
  private WxMpMessageHandler reply() {
    WxMpMessageHandler test = new WxMpMessageHandler() {
      public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
          WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("维护中。。。").fromUser(wxMessage.getToUserName())
            .toUser(wxMessage.getFromUserName()).build();
        return m;
      }
    };
    return test;
  }
  private WxMpMessageHandler fun() {
    WxMpMessageHandler test = new WxMpMessageHandler() {
      public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
          WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String lang = "zh_CN"; //语言
        WxMpUser user = wxMpService.userInfo(wxMessage.getFromUserName(), lang);
        log.info("user="+user.toString());
        //同步回复消息
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription(user.getProvince());
        item.setPicUrl(user.getHeadImgUrl());
        item.setTitle(user.getNickname());
        item.setUrl("http://www.baidu.com");

        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
          .fromUser(wxMessage.getToUserName())
          .toUser(wxMessage.getFromUserName())
          .addArticle(item)
          .build();
        
        //主动发送消息
/*        WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
        article1.setUrl("www.baidu.com");
        article1.setPicUrl("www.baidu.com");
        article1.setDescription("Is Really A Happy Day");
        article1.setTitle("Happy Day");

        WxMpCustomMessage.WxArticle article2 = new WxMpCustomMessage.WxArticle();
        article2.setUrl("www.baidu.com");
        article2.setPicUrl("www.baidu.com");
        article2.setDescription("Is Really A Happy Day");
        article2.setTitle("Happy Day");

        WxMpCustomMessage message = WxMpCustomMessage.NEWS()
            .toUser(wxMessage.getFromUserName())
            .addArticle(article1)
            .addArticle(article2)
            .build();
        wxMpService.customMessageSend(message);*/
        return m;
      }
    };
    
    return test;
  }

}
