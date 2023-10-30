package com.sisp.controller;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/22 10:43 下午
 * @Version 1.0
 */
public class QuestionOptionVO {
    private String id;

    public QuestionOptionVO(String id) {
        this.id = id;
    }

    public QuestionOptionVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QuestionOptionVO{" +
                "id='" + id + '\'' +
                '}';
    }
}
