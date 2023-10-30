package com.sisp.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ProjectEntity implements Serializable {
    private String id;
    private String username;
    private String projectName;
    private String projectContent;
    private String createBy;
    private Date createDate;
    private String lastUpdateBy;
    private Date lastUpdateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectContent='" + projectContent + '\'' +
                ", creatBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateBy='" + lastUpdateBy + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
