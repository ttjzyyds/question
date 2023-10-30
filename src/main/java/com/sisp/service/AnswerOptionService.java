package com.sisp.service;

import com.sisp.dao.AnswerEntityMapper;
import com.sisp.dao.AnswerOptionEntityMapper;
import com.sisp.dao.entity.AnalysisVO;
import com.sisp.dao.entity.AnswerOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 11:00 上午
 * @Version 1.0
 */
@Service
public class AnswerOptionService {

    @Autowired
    private AnswerOptionEntityMapper answerOptionEntityMapper;


    public List<AnswerOption> selectListById(Integer id) {
        return answerOptionEntityMapper.selectListById(id);
    }

    public List<AnalysisVO> analysis(String id, int i) {
        return answerOptionEntityMapper.analysis(id, i);
    }

    public List<AnalysisVO> analysisByType(String question_id, String type) {
        return answerOptionEntityMapper.analysisByType(question_id, type);
    }
}
