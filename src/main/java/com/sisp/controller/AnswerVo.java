package com.sisp.controller;

import com.sisp.dao.entity.Answer;
import com.sisp.dao.entity.AnswerOption;
import com.sisp.dao.entity.QuestionOption;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 10:50 上午
 * @Version 1.0
 */
public class AnswerVo {
    private Integer id;

    private List<AnswerOption> list;

    private QuestionOption questionOption;

    public AnswerVo(Integer id, List<AnswerOption> list, QuestionOption questionOption) {
        this.id = id;
        this.list = list;
        this.questionOption = questionOption;
    }

    public AnswerVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<AnswerOption> getList() {
        return list;
    }

    public void setList(List<AnswerOption> list) {
        this.list = list;
    }

    public QuestionOption getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(QuestionOption questionOption) {
        this.questionOption = questionOption;
    }

    @Override
    public String toString() {
        return "AnswerVo{" +
                "id='" + id + '\'' +
                ", list=" + list +
                ", questionOption=" + questionOption +
                '}';
    }
}
