package com.sisp.controller;

import com.sisp.dao.entity.QuestionOption;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 9:57 上午
 * @Version 1.0
 */
public class OptionVO extends QuestionOption {

    private String questionnairename;

    public String getQuestionnairename() {
        return questionnairename;
    }

    public void setQuestionnairename(String questionnairename) {
        this.questionnairename = questionnairename;
    }

    public OptionVO() {
    }

    public OptionVO(String id, String question_id, String sequence, String option_content, String questionnairename) {
        super(id, question_id, sequence, option_content);
        this.questionnairename = questionnairename;
    }

    public OptionVO(String questionnairename) {
        this.questionnairename = questionnairename;
    }
}
