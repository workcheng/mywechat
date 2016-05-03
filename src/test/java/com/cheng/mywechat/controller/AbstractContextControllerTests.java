package com.cheng.mywechat.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

//XML风格  
@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration()  
@ContextHierarchy({  
      @ContextConfiguration(name = "parent", locations = "classpath:/config/spring/applicationContext.xml"),  
      @ContextConfiguration(name = "child", locations = "classpath:/config/springmvc-servlet.xml")  
})  

//注解风格  
//@RunWith(SpringJUnit4ClassRunner.class)  
//@WebAppConfiguration(value = "src/main/webapp")  
//@ContextHierarchy({  
//      @ContextConfiguration(name = "parent", classes = AppConfig.class),  
//      @ContextConfiguration(name = "child", classes = MvcConfig.class)  
//})  
public class AbstractContextControllerTests {

  @Autowired
  protected WebApplicationContext wac;

}
