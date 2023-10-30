package com.sisp.dao;

import com.sisp.dao.entity.AnalysisVO;
import com.sisp.dao.entity.Answer;
import com.sisp.dao.entity.QuestionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 10:40 上午
 * @Version 1.0
 */
@Repository
@Mapper
@Component
public interface AnswerEntityMapper {

    List<Answer> selectList();

    String selectQuestionIdById(Integer id);

    Answer selectAnswerByQuestionId(String id);

    List<Map<String, Object>> getQuestionById(String id);

    List<Map<String, Object>> getOptionById(Object questionId);

    void insertQuestion(QuestionInfo questionInfo);

    void updateQuestion(QuestionInfo questionInfo);

    void delete(String id);
}
