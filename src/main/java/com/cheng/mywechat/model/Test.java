package com.cheng.mywechat.model;

import java.io.Serializable;
import java.sql.Date;

public class Test implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5211213395438021958L;
  private int               test_id;
  private String            test_name;
  private Date              time;

  public int getTest_id() {
    return test_id;
  }

  public void setTest_id(int test_id) {
    this.test_id = test_id;
  }

  public String getTest_name() {
    return test_name;
  }

  public void setTest_name(String test_name) {
    this.test_name = test_name;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

}
