package com.sisp.service;

import com.sisp.dao.AnswerEntityMapper;
import com.sisp.dao.entity.AnalysisVO;
import com.sisp.dao.entity.Answer;
import com.sisp.dao.entity.QuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 10:40 上午
 * @Version 1.0
 */
@Service
public class AnswerService {

    @Autowired
    private AnswerEntityMapper answerEntityMapper;

    public List<Answer> selectAnswerList() {
        return answerEntityMapper.selectList();
    }

    public String selectQuestionIdById(Integer id) {
        return answerEntityMapper.selectQuestionIdById(id);
    }

    public Answer selectAnswerByQuestionId(String id) {
        return answerEntityMapper.selectAnswerByQuestionId(id);
    }

    public List<Map<String, Object>> getQuestionById(String id) {
        return answerEntityMapper.getQuestionById(id);
    }

    public List<Map<String, Object>> getOptionById(Object questionId) {
        return answerEntityMapper.getOptionById(questionId);
    }

    public void insertQuestion(QuestionInfo questionInfo) {
        answerEntityMapper.insertQuestion(questionInfo);
    }

    public void updateQuestion(QuestionInfo questionInfo) {
        answerEntityMapper.updateQuestion(questionInfo);
    }

    public void delete(String id) {
        answerEntityMapper.delete(id);
    }
}
