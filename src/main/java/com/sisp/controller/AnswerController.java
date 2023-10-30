package com.sisp.controller;

import com.sisp.dao.entity.*;
import com.sisp.service.AnswerOptionService;
import com.sisp.service.AnswerService;
import com.sisp.service.QuestionOptionService;
import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 10:39 上午
 * @Version 1.0
 */
@Controller
@RequestMapping("/answer")
@CrossOrigin
public class AnswerController {


    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionOptionService questionOptionService;

    @Autowired
    private AnswerOptionService answerOptionService;



    @PostMapping("/list")
    @ResponseBody
    public List<Answer> selectAnswerList() {
        return answerService.selectAnswerList();
    }

    @PostMapping("/info")
    @ResponseBody
    public AnswerVo getAnswerInfo(@RequestBody AnswerVo answerVo) {
        String question_id = answerService.selectQuestionIdById(answerVo.getId());
        AnswerVo answer = new AnswerVo();
        QuestionOption questionOption = questionOptionService.selectQuestionOptionById(question_id);
        answer.setQuestionOption(questionOption);
        List<AnswerOption>  list = answerOptionService.selectListById(answerVo.getId());
        answer.setList(list);
        return answer;
    }

    @PostMapping("/analysis")
    @ResponseBody
    public List analysis(@RequestBody QuestionVO questionVO) {
        String id = questionVO.getId();
        Answer answer = answerService.selectAnswerByQuestionId(id);
        if (answer == null)
            return null;
        //分析每道题
        List<List<AnalysisVO>> res = new ArrayList<>();
        for (int i = 1; i <= answer.getCount(); i++) {
            List<AnalysisVO> list =  answerOptionService.analysis(answer.getQuestion_id(), i);
            res.add(list);
        }
        //分析单选题
        List<AnalysisVO> l1 = answerOptionService.analysisByType(answer.getQuestion_id(), "单选");
        res.add(l1);
        //分析多选题
        List<AnalysisVO> l2 = answerOptionService.analysisByType(answer.getQuestion_id(), "多选");
        res.add(l2);
        return res;
    }


}
