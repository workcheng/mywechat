package com.cheng.mywechat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/base")
public class TestController {
  
  @RequestMapping(value="/test")
  public @ResponseBody Object test(){
    Map<String,Object> map = new HashMap<>();
    List<String> list= new ArrayList<>();
    list.add("c++");
    list.add("c");
    map.put("relax", list);
    return map; 
  }
  
}
