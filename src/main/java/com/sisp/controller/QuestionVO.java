package com.sisp.controller;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/22 6:59 下午
 * @Version 1.0
 */
public class QuestionVO {

    String id;

    public QuestionVO(String id) {
        this.id = id;
    }

    public QuestionVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QuestionVO{" +
                "id='" + id + '\'' +
                '}';
    }
}
