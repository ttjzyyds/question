package com.sisp.dao;

import com.sisp.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component

public interface UserEntityMapper {
    //查询用户列表
    List<UserEntity> queryUserList(UserEntity userEntity);

    //创建用户信息
    int insert(UserEntity userEntity);

    //根据ID删除用户信息
    int deleteUserByName(UserEntity userEntity);

    //修改用户信息updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(UserEntity userEntity);

   //查询用户
    List<UserEntity> selectUserInfo(UserEntity userEntity);

    // 更新用户状态
    int updateUserStatus(UserEntity userEntity);

}
