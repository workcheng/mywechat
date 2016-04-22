package com.cheng.mywechat.dao.test;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.cheng.mywechat.dao.BaseDaoImpl;
import com.cheng.mywechat.model.Test;

@Repository
public class TestDao extends BaseDaoImpl{

  public Test getTest() throws SQLException{
    return super.sqlSessionTemplate.selectOne("com.cheng.mywechat.dao.test.TestDaoMapper.getTest");
  }
}
