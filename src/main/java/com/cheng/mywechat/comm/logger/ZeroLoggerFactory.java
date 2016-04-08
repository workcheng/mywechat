package com.cheng.mywechat.comm.logger;

import org.slf4j.LoggerFactory;

/**
 * 
 * @author BenjaminXi
 * @date 2015-6-6 It wrap slf4j factory to create our log class.
 */
public class ZeroLoggerFactory {

  public static ZeroLogger getLogger(Class<?> clazz) {
    ZeroLogger logger = new ZeroLogger(LoggerFactory.getLogger(clazz));
    return logger;
  }
}
