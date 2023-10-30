package com.sisp.controller;



import com.sisp.beans.HttpResponseEntity;
import com.sisp.dao.entity.QuestionnaireEntity;
import com.sisp.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
//@RequestMapping("")
@CrossOrigin
public class questionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @RequestMapping(value = "queryquestionnaireList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryquestionnaireList(@RequestBody QuestionnaireEntity QuestionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            List<QuestionnaireEntity> results = questionnaireService.queryquestionnaireList(QuestionnaireEntity);
            if (CollectionUtils.isEmpty(results)) {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(results.get(0));
                httpResponseEntity.setMessage("无问卷信息");
            } else {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(results);
                httpResponseEntity.setMessage("查询成功");
            }
        } catch (Exception e) {
            System.out.println("异常出现");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    @PostMapping("/selectQuestionByProId")
    public List<QuestionnaireEntity> selectQuestionByProId(@RequestBody QuestionVO questionVO) {
        System.out.println(questionVO.getId());
        return questionnaireService.selectQuestionByProId(questionVO.getId());
    }

    @PostMapping("/editQuestionById")
    public QuestionnaireEntity editQuestionById(@RequestBody QuestionVO questionVO) {
        QuestionnaireEntity QuestionnaireEntity = questionnaireService.editQuestionById(questionVO.getId());
        return QuestionnaireEntity;
    }

    @PostMapping("/delQuestionById")
    public boolean delQuestionById(@RequestBody QuestionVO questionVO) {
        System.out.println("cooool");
        System.out.println(questionVO.getId());
        return questionnaireService.deleteQuestionnaireById(questionVO.getId());
    }


    //发布
    @PostMapping("/publishQuestionnaire/{questionnaireId}")
    public void publishQuestionnaire(@PathVariable Long questionnaireId) {
        questionnaireService.publishQuestionnaire(questionnaireId);
    }
    @GetMapping("/questionnaireLink/{questionnaireId}")
    public String getQuestionnaireLink(@PathVariable Long questionnaireId) {
        return questionnaireService.generateUniqueLink(questionnaireId);
    }

    // 添加
    @RequestMapping(value = "/createQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addquestionnaireInfo(@RequestBody QuestionnaireEntity QuestionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            QuestionnaireEntity.setIssue_time(new Date());
            int result = questionnaireService.createquestionnaire(QuestionnaireEntity);
            if (result != 0) {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("创建成功");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("创建失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    // 修改
    @RequestMapping(value = "modifyquestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyquestionnaireInfo(@RequestBody QuestionnaireEntity QuestionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int result = questionnaireService.updatequestionnaire(QuestionnaireEntity);
            if (result != 0) {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("修改成功");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("修改失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/deletequestionnaireById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deletequestionnaire(@RequestBody QuestionnaireEntity QuestionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            boolean result = questionnaireService.deleteQuestionnaireById(QuestionnaireEntity.getId());
            if (result) {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("删除成功");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("删除失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            httpResponseEntity.setCode("0");
            httpResponseEntity.setData(false);
            httpResponseEntity.setMessage("删除异常");
        }
        return httpResponseEntity;
    }


    // 添加
    @RequestMapping(value = "/generateLink", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity generateLink(@RequestBody QuestionnaireEntity questionnaireEntity) {
        System.out.println(questionnaireEntity);
        String link= UUID.randomUUID().toString();
        System.out.println(questionnaireEntity.getQuestionnaireName());
        int i=questionnaireService.updateLink(questionnaireEntity,link);
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            if (i != 0) {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(link);
                httpResponseEntity.setMessage("创建成功");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("创建失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(link);
        return httpResponseEntity;
    }


}

