package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.entity.ProjectEntity;
import com.sisp.dao.ProjectEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectEntityMapper projectEntityMapper;

    //查询列表
    public List<ProjectEntity> queryProjectList(ProjectEntity projectEntity){
        //处理查询数据，模糊查询
        String projectName = projectEntity.getProjectName();
        if(projectName != null && projectName.length()>0){
            projectEntity.setProjectName("%"+projectName+"%");
        }

        List<ProjectEntity> result = projectEntityMapper.queryProjectList(projectEntity);
        /*System.out.println(result);*/
        return  result;
    }
    //创建项目
    public int addProjectInfo(ProjectEntity projectEntity){
        projectEntity.setId(UUIDUtil.getOneUUID());
        int projectResult = projectEntityMapper.insert(projectEntity);
        if(projectResult !=0){
            return 3;//3代表项目存在
        }else{
            return projectResult;
        }
    }

    //修改项目信息
    public int modifyProjectInfo(ProjectEntity projectEntity){
        int projectResult = projectEntityMapper.updateByPrimaryKeySelective(projectEntity);
        return projectResult;
    }

    //删除项目信息
    public int deleteProjectById(ProjectEntity projectEntity){
        int projectResult = projectEntityMapper.deleteProjectById((projectEntity));
        return projectResult;
    }

    public ProjectEntity selectProByid(String id) {
        return projectEntityMapper.selectProById(id);
    }
}
