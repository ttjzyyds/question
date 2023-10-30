package com.sisp.dao.entity;

import java.util.Date;

public class QuestionnaireEntity {
    private String id;
    private String projectname;

    public String getProjectname() {
        return projectname;
    }

    @Override
    public String toString() {
        return "QuestionnaireEntity{" +
                "id='" + id + '\'' +
                ", projectname='" + projectname + '\'' +
                ", questionnairename='" + questionnaireName + '\'' +
                ", description='" + description + '\'' +
                ", target='" + target + '\'' +
                ", status='" + status + '\'' +
                ", issueTime=" + issue_time +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    private String questionnaireName;
    private String description;
    private String target;
    private String status;
    private Date issue_time;
    private String startTime;
    private String endTime;
    private String link;
    private String del;

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    // 构造方法、Getter和Setter方法等根据需要添加

    // 注意：实体类中的属性名要与数据库表中的字段名对应

    // 示例实现部分
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getIssue_time() {
        return issue_time;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
