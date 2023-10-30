package com.sisp;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.common.utils.UUIDUtil;
import com.sisp.controller.ProjectController;
import com.sisp.controller.UserController;
import com.sisp.dao.AnswerEntityMapper;
import com.sisp.dao.ProjectEntityMapper;
import com.sisp.dao.UserEntityMapper;
import com.sisp.dao.entity.Answer;
import com.sisp.dao.entity.ProjectEntity;
import com.sisp.dao.entity.UserEntity;
import com.sisp.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;


import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.util.CollectionUtils;

import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
//    @Test
//    void contextLoads() {

    //    }
    private UserController userController;


   /* @Test
    public void testSelect(){
        UserEntity userEntity =new UserEntity();
        userEntity.setUsername("admin");
        HttpResponseEntity httpResponseEntity = userController.queryUserList(userEntity);
        log.info("---结果---");
        log.info(httpResponseEntity.getData().toString());
    }*/

    Logger log = Logger.getLogger(DemoApplicationTests.class);

    @Autowired
    ProjectEntityMapper mapper;
    @Autowired
    ProjectController controller;

//    @Test
    public void queryUserList() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        List<UserEntity> list = userEntityMapper.queryUserList(userEntity);
        if (CollectionUtils.isEmpty(list)) {
            // 记录error级别的信息
        } else {
            System.out.println(list);
            // 记录info级别的信息
            log.info(">>queryUserList用户列表查询测试成功");
        }
    }

    @Test
    public void selectUserInfo() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("123");
        List<UserEntity> list = userEntityMapper.selectUserInfo(userEntity);
        if (CollectionUtils.isEmpty(list)) {
            // 记录error级别的信息
        } else {
            System.out.println(list);
            // 记录info级别的信息
            log.info(">>qselectUserInfo用户登录测试成功");
        }
    }

    @Test
    public void insert() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUIDUtil.getOneUUID());
        userEntity.setStatus("1");
        userEntity.setUsername("LS");
        userEntity.setPassword("123");
        int i = userEntityMapper.insert(userEntity);
        if (i == 0) {
            // 记录error级别的信息
        } else {
            System.out.println(i);
            // 记录info级别的信息
            log.info(">>insert用户插入测试成功");
        }
    }

    @Test
    public void deleteUserByName() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("aaaaa");
        int i = userEntityMapper.deleteUserByName(userEntity);
        if (i != 0) {
            // 记录error级别的信息
        } else {
            System.out.println(i);
            // 记录info级别的信息
            log.info(">>delete用户删除测试成功");
        }
    }
/*
@Test
    void testGetProjectList() {
        HttpResponseEntity responseEntity = controller.queryProjectList(null);
        System.out.println(responseEntity);
        List<ProjectEntity> dat = (List<ProjectEntity>) responseEntity.getData();
        for (ProjectEntity projectEntity:dat){
            System.out.println(projectEntity);
        }



    }*/


    @Test
    public void queryProjectList() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建projectMapper对象，mybatis自动生成mapper代理对象
        ProjectEntityMapper projectEntityMapper = sqlSession.getMapper(ProjectEntityMapper.class);
        //调用projectMapper的方法
        ProjectEntity projectEntity = new ProjectEntity();
        List<ProjectEntity> list = projectEntityMapper.queryProjectList(projectEntity);
        if(CollectionUtils.isEmpty(list)){
            // 记录error级别的信息
        }else{
            System.out.println(list);
            // 记录info级别的信息
            log.info(">>queryProjectList项目列表查询测试成功");
        }
    }
    @Test
    public void addProjectInfo() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建projectMapper对象，mybatis自动生成mapper代理对象
        ProjectEntityMapper projectEntityMapper = sqlSession.getMapper(ProjectEntityMapper.class);
        //调用projectMapper的方法
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("888");
        int i = projectEntityMapper.insert(projectEntity);
        if(i==0){
            // 记录error级别的信息
        }else{
            System.out.println(i);
            // 记录info级别的信息
            log.info(">>addProjectInfo新增项目测试成功");
        }
    }

    @Test
    public void deleteProjectById() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ProjectEntityMapper ProjectEntityMapper = sqlSession.getMapper(ProjectEntityMapper.class);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("123");
        int i = ProjectEntityMapper.deleteProjectById(projectEntity);
        if (i != 0) {
            // 记录error级别的信息
        } else {
            System.out.println(i);
            // 记录info级别的信息
            log.info(">>删除项目测试成功");
        }
    }

    @Test
    public void modifyProjectInfo() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建projectMapper对象，mybatis自动生成mapper代理对象
        ProjectEntityMapper projectEntityMapper = sqlSession.getMapper(ProjectEntityMapper.class);
        //调用projectMapper的方法
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("1");
        projectEntity.setProjectName("修改项目名称");
        projectEntity.setProjectContent("修改项目描述");
        int i = projectEntityMapper.updateByPrimaryKeySelective(projectEntity);
        if(i==0){
            // 记录error级别的信息
        }else{
            System.out.println(i);
            // 记录info级别的信息
            log.info(">>modifyProjectInfo修改项目信息测试成功");
        }
    }

    }
