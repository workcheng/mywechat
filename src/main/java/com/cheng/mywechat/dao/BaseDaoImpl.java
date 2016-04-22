package com.cheng.mywechat.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDaoImpl {
  protected @Resource(name = "sqlSessionTemplate") SqlSessionTemplate   sqlSessionTemplate;
}
