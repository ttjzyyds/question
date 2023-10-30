package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.dao.entity.QuestionOption;
import com.sisp.service.QuestionOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/22 9:51 下午
 * @Version 1.0
 */
@RequestMapping("/admin")
@Controller
@CrossOrigin
public class QuestionOptionController {

    @Autowired
    private QuestionOptionService questionOptionService;


    @PostMapping("/modifyQuestionnaire")
    public HttpResponseEntity addQuestionOption(@RequestBody QuestionOption questionOption) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (questionOption.getId() == null) {
            String s = questionOptionService.addQuestionOption(questionOption);
            if (!s.equals("")) {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setMessage("新增成功!");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setMessage("新增失败!");
            }
        } else {
            boolean flag = questionOptionService.updateQuestionOption(questionOption);
            if (flag) {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setMessage("修改成功!");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setMessage("修改失败!");
            }
        }

        return httpResponseEntity;
    }

    @PostMapping("/selectQuestionOptionById")
    @ResponseBody
    public QuestionOption selectQuestionOptionById(@RequestBody QuestionVO questionVO) {
        QuestionOption questionOption = questionOptionService.selectQuestionOptionById(questionVO.getId());
        System.out.println(questionOption);
        return questionOption;
    }

    @PostMapping("/selectQuestionOptionList")
    @ResponseBody
    public List<OptionVO> selectQuestionOptionList() {
        return questionOptionService.selectQuestionOptionList();
    }

}
