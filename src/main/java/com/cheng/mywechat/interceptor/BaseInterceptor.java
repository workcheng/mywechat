package com.cheng.mywechat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.cheng.mywechat.comm.logger.ZeroLogger;
import com.cheng.mywechat.comm.logger.ZeroLoggerFactory;

public class BaseInterceptor extends AbstractBaseInterceptor{
    
  private static ZeroLogger log = ZeroLoggerFactory.getLogger(BaseInterceptor.class);

    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
      doFilter(request,response);
      String url = request.getRequestURL().toString();
      log.error("base url="+url);
      return true;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(
        HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(
        HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
    }
}
