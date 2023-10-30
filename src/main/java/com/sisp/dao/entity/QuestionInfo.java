package com.sisp.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QuestionInfo {

    private String id;
    private String type;
    private String order;
    private String content;
    private String questionnaire_id;

}
