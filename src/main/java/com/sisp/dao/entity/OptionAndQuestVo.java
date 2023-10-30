package com.sisp.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class OptionAndQuestVo {

    private String questionnaire_id; //问卷表id
    private String id;
    private String type;//题目类型
    private String content;//题目内容
    private String order;//题目序号
    private List<Map<String,Object>> listByOption; //选项数组
}
