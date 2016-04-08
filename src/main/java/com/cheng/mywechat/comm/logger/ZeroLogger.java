package com.cheng.mywechat.comm.logger;

import org.slf4j.Logger;

import com.cheng.mywechat.comm.context.SysContext;

/**
 * 
 * @author BenjaminXi
 * @date 2015-6-6 It wrap a logger class to append trackingID into the log file.
 */

public class ZeroLogger {

  private Logger log;

  ZeroLogger(Logger log) {
    this.log = log;
  }

  public void error(String message, Object e) {
    log.error(buildTrackingIdInMessage(message), e);
  }

  public void error(String message) {
    log.error(buildTrackingIdInMessage(message));
  }

  public void info(String message) {
    log.info(buildTrackingIdInMessage(message));
  }

  public void debug(String message) {
    log.debug(buildTrackingIdInMessage(message));
  }

  private String buildTrackingIdInMessage(String message) {
    return "[TrackingId = " + SysContext.getTrackingId() + "] " + message;
  }
}
