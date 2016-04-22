package com.cheng.mywechat.service.test;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cheng.mywechat.dao.test.TestDao;
import com.cheng.mywechat.model.Test;

@Service
public class TestService {
  @Autowired 
  TestDao testDao;
  
  public Test getTest() throws SQLException{
    return testDao.getTest();
  }
}
