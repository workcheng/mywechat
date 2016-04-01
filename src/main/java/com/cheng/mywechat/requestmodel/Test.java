package com.cheng.mywechat.requestmodel;

import org.hibernate.validator.constraints.NotEmpty;

public class Test {
  @NotEmpty
  private String skillName;
  private String desc;

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getSkillName() {
    return skillName;
  }

  public void setSkillName(String skillName) {
    this.skillName = skillName;
  }
}
