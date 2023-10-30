package com.sisp.dao.entity;

public class QuestionOption {
    private String id;
    private String question_id;
    private String sequence;//说明
    private String option_content;//内容html

    public QuestionOption(String id, String question_id, String sequence, String option_content) {
        this.id = id;
        this.question_id = question_id;
        this.sequence = sequence;
        this.option_content = option_content;
    }

    public QuestionOption() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getOption_content() {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }

    @Override
    public String toString() {
        return "QuestionOption{" +
                "id='" + id + '\'' +
                ", question_id='" + question_id + '\'' +
                ", sequence='" + sequence + '\'' +
                ", option_content='" + option_content + '\'' +
                '}';
    }
}
