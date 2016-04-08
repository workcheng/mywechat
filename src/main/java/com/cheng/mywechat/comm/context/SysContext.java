package com.cheng.mywechat.comm.context;

/**
 * 
 * @author BenjaminXi
 * @date 2015-6-6 It is a class to take care of the some context in threadlocal.
 */
public class SysContext {
  private static ThreadLocal<String> trackingId = new ThreadLocal<String>();

  public static void setTrackingId(String value) {
    trackingId.set(value);
  }

  public static String getTrackingId() {
    return trackingId.get();
  }
}
