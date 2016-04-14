package com.cheng.mywechat.controller;

public class Test {

  public static void main(String args[]){
    String url = "http://localhost:8888/mywechat/base/test";
    String mappingURL = ".*/base/.*";
    System.out.println(url.matches(mappingURL));
  }
}
