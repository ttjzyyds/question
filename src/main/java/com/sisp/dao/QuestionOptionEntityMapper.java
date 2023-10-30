package com.sisp.dao;

import com.sisp.controller.OptionVO;
import com.sisp.dao.entity.QuestionOption;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/22 9:58 下午
 * @Version 1.0
 */
@Repository
@Mapper
@Component
public interface QuestionOptionEntityMapper {

    public boolean addQuestionOption(QuestionOption questionOption);


    public int selectCountByQuestionId(String question_id);

    QuestionOption selectQuestionOptionById(String id);

    boolean updateQuestionOption(QuestionOption questionOption);

    List<OptionVO> selectQuestionOptionList();

    void delete(String id);
}
