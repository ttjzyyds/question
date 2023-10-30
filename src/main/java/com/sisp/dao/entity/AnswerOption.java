package com.sisp.dao.entity;

public class AnswerOption {
    private String question_id;
    private Integer id;
    private Integer answer_id;
    private String type;
    private Integer no;
    private String content;

    public AnswerOption(String question_id, Integer id, Integer answer_id, String type, Integer no, String content) {
        this.question_id = question_id;
        this.id = id;
        this.answer_id = answer_id;
        this.type = type;
        this.no = no;
        this.content = content;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public AnswerOption() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AnswerOption{" +
                "id=" + id +
                ", answer_id=" + answer_id +
                ", type='" + type + '\'' +
                ", no=" + no +
                ", content='" + content + '\'' +
                '}';
    }
}
