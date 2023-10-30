package com.sisp.dao.entity;

import java.util.Date;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 10:36 上午
 * @Version 1.0
 */
public class Answer {

    private Integer id;
    private String question_id;
    private Integer count;

    private String uname;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question_id='" + question_id + '\'' +
                ", count=" + count +
                ", uname='" + uname + '\'' +
                ", answerTime=" + answerTime +
                '}';
    }

    private Date answerTime;

    public Answer(Integer id, String question_id, Integer count) {
        this.id = id;
        this.question_id = question_id;
        this.count = count;
    }

    public Answer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
