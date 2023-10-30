package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.entity.OptionAndQuestVo;
import com.sisp.dao.entity.ProjectEntity;
import com.sisp.dao.entity.QuestionInfo;
import com.sisp.dao.entity.QuestionOption;
import com.sisp.service.AnswerService;
import com.sisp.service.ProjectService;
import com.sisp.service.QuestionOptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
//@RequestMapping("/admin")
//@RequestMapping("")
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionOptionService questionOptionService;

    @RequestMapping(value = "queryProjectList",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@RequestBody ProjectEntity projectEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            List<ProjectEntity> results = projectService.queryProjectList(projectEntity);
            if(CollectionUtils.isEmpty(results)){
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(results.get(0));
                httpResponseEntity.setMessage("无项目信息");
            }else {
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(results);
                httpResponseEntity.setMessage("查询成功");
            }
        }catch (Exception e){
            System.out.println("异常出现");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    @PostMapping("/queryProList")
    public List<ProjectEntity> queryProList() {
        List<ProjectEntity> results = projectService.queryProjectList(new ProjectEntity());
        return results;
    }

    @PostMapping("/admin/selectProById")
    public ProjectEntity    selectProById(@RequestBody ProjectEntity projectEntity) {
        ProjectEntity pro = projectService.selectProByid(projectEntity.getId());
        return pro;
    }

    //添加
    @RequestMapping(value = "/addProjectInfo",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            int result = projectService.addProjectInfo(projectEntity);
            if(result!=0){
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("创建成功");
            }else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("创建失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
        return httpResponseEntity;
    }

    //用户修改
    @RequestMapping(value = "/admin/modifyProjectInfo",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity modifyUserInfo(@RequestBody ProjectEntity projectEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //System.out.println("");
        try{
            int result = projectService.modifyProjectInfo(projectEntity);
            if(result!=0){
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("修改成功");
            }else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("修改失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    //用户删除
    @RequestMapping(value = "/deleteProjectById",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity deleteProject(@RequestBody ProjectEntity projectEntity){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            int result = projectService.deleteProjectById(projectEntity);
            if(result!=0){
                httpResponseEntity.setCode("666");
                httpResponseEntity.setData(result);
                httpResponseEntity.setMessage("删除成功");
            }else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(0);
                httpResponseEntity.setMessage("删除失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }


    /**
     * questionnaire_info  问卷表
     *
     * question_info   题目表
     *
     * option_info  选项表
     *
     * answer_option  答案表 -- 不动
     *
     *
     */
    //根据问卷id  获取题目和选项
    @GetMapping("/getQuestionById")
    public SysResult getQuestionById(String id){

        List<Map<String,Object>> list = answerService.getQuestionById(id); //根据调查问卷id获取当前问卷的题目列表

        for (Map<String,Object> map : list){
            Object questionId = map.get("id"); //题目id
            List<Map<String,Object>> listByOption = answerService.getOptionById(questionId);//选项列表

            map.put("listByOption",listByOption);
        }
        return SysResult.success().setData(list);
    }




    /**
     *  添加题目 和 选项
     * @param vos
     * @return
     */
    @PostMapping("/updateANDInsertQuestandOption")
    public SysResult updateQuestandOption(@RequestBody List<OptionAndQuestVo> vos){

//        List<Map<String, Object>> listByOption = vo.getListByOption();
//
        for(OptionAndQuestVo vo : vos){
            String uuid = UUID.randomUUID().toString().replace("-", "");

            List<Map<String, Object>> listByOption = vo.getListByOption();
            for (Map<String,Object> map : listByOption){
                Object id = map.get("id");

                String sequence = map.get("sequence").toString();
                String option_content = map.get("option_content").toString();
                QuestionOption questionOption = new QuestionOption();
                questionOption.setQuestion_id(uuid);
                questionOption.setOption_content(option_content);
                questionOption.setSequence(sequence);

                if (id != null){ //如果携带id表示修改  不带表示新增
                    questionOption.setId(id.toString());
                    questionOptionService.updateQuestionOption(questionOption);
                    continue;
                }

                String s = questionOptionService.addQuestionOption(questionOption);//添加到选项表中
            }

            String id1 = vo.getId() == null ? "" : vo.getId();
            if (!id1.equals("")){
                QuestionInfo questionInfo = new QuestionInfo();
                BeanUtils.copyProperties(vo,questionInfo);
                answerService.updateQuestion(questionInfo);
                continue;
            }
            QuestionInfo questionInfo = new QuestionInfo();
            BeanUtils.copyProperties(vo,questionInfo);
            questionInfo.setId(uuid);
            answerService.insertQuestion(questionInfo);
        }

        return SysResult.success();
    }

    /**
     * 删除题目表数据
     * @param id
     * @return
     */
    @GetMapping("deleteByQ")
    public SysResult deleteByQ(String id){
        questionOptionService.delete(id);
        return SysResult.success();
    }

    /**
     * 删除选项表数据
     * @param id
     * @return
     */
    @GetMapping("deleteByO")
    public SysResult deleteByO(String id){
        answerService.delete(id);
        return SysResult.success();
    }
}
