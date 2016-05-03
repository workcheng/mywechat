package com.cheng.mywechat.controller.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cheng.mywechat.controller.AbstractContextControllerTests;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTests extends AbstractContextControllerTests{

  private MockMvc   mockMvc;
  
  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(this.wac).build();
  }

  @Test
  public void test() throws Exception {
    Map<String, Object> map = new HashMap<>();
    List<String> list = new ArrayList<>();
    list.add("c++");
    list.add("c");
    map.put("relax", list);
    this.mockMvc.perform(get("/base/test").accept(MediaType.APPLICATION_JSON))
    .andExpect(content().string(new ObjectMapper().writeValueAsString(map)));
  }
  
  @Test
  public void giveMeAUser() throws Exception {
    com.cheng.mywechat.model.Test test = new com.cheng.mywechat.model.Test();
    test.setTest_id(1);
    test.setTest_name("test_name");
    test.setTime(new Date(System.currentTimeMillis()));
    this.mockMvc.perform(get("/base/test/mybatis").accept(MediaType.APPLICATION_JSON))
    .andExpect(content().string(new ObjectMapper().writeValueAsString(test)));
  }
}
