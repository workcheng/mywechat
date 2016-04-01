package com.zerogmi.match.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zerogmi.match.requestmodel.Test;

@Controller
@RequestMapping("/base")
public class TestController {
  
  @RequestMapping(value="/test")
  public @ResponseBody Object test(@RequestBody Test test){
    test.getSkillName();
    Map<String,Object> map = new HashMap<>();
    List<String> list= new ArrayList<>();
    list.add("c++");
    list.add("c");
    map.put("relax", list);
    return map;
  }
  
}
