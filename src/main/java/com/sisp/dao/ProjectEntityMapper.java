package com.sisp.dao;

import com.sisp.dao.entity.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component

public interface ProjectEntityMapper {
    //查询项目列表
    List<ProjectEntity> queryProjectList(ProjectEntity projectEntity);

    //新增
    int insert(ProjectEntity projectEntity);

    //删除
    int deleteProjectById (ProjectEntity projectEntity);

    //修改
    int updateByPrimaryKeySelective(ProjectEntity projectEntity);

    //根据id查询project信息
    ProjectEntity selectProById(String id);

    //查询项目
    //List<ProjectEntity> selectProjectInfo(ProjectEntity projectEntity);
}
