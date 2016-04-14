package com.cheng.mywechat.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cheng.mywechat.comm.constant.CommonConstant;
import com.cheng.mywechat.comm.context.SysContext;
import com.cheng.mywechat.comm.uuid.ZGUUID;

import me.chanjar.weixin.common.util.StringUtils;

public abstract class AbstractBaseInterceptor extends HandlerInterceptorAdapter{

  public void doFilter(ServletRequest req, ServletResponse res)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    String trakcingId = request.getHeader(CommonConstant.TRACKING_ID);
    if (StringUtils.isBlank(trakcingId)) {
      trakcingId = ZGUUID.random();
      SysContext.setTrackingId(trakcingId);
      response.setHeader(CommonConstant.TRACKING_ID, trakcingId);
    }    
  }

  protected String getToken(HttpServletRequest request, HttpServletResponse response) {
    String token = request.getHeader("ZG-TOKEN");
    if (null == token) {
      Object sessionToken = request.getSession().getAttribute("token");
      if (null != sessionToken) {
        token = sessionToken.toString();
      }
    }
    return token;
  }
}
