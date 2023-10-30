package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.QuestionnaireEntityMapper;
import com.sisp.dao.entity.QuestionnaireEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    // 查询问卷列表
    public List<QuestionnaireEntity> queryquestionnaireList(QuestionnaireEntity QuestionnaireEntity) {
        String questionnairename = QuestionnaireEntity.getQuestionnaireName();
        if(questionnairename != null && questionnairename.length()>0){
            QuestionnaireEntity.setQuestionnaireName("%"+questionnairename+"%");
        }
        List<QuestionnaireEntity> result = questionnaireEntityMapper.queryQuestionnaireList(QuestionnaireEntity);
        return result;
    }

    // 创建问卷
    public int createquestionnaire(QuestionnaireEntity questionnaireEntity) {
        questionnaireEntity.setId(UUIDUtil.getOneUUID());
        int insertResult = questionnaireEntityMapper.insert(questionnaireEntity);
        if(insertResult !=0){
            return 3;//3代表存在
        }else{
            return insertResult;
        }
    }

    public boolean deleteQuestionnaireById(String id) {
        System.out.println(id);
        QuestionnaireEntity questionnaire = questionnaireEntityMapper.selectQuestionById(id);
        System.out.println(questionnaire);
        if (questionnaire.getStatus().equals("未发布")) {
            questionnaireEntityMapper.markQuestionnaireAsDeleted(id); // 设置删除标签为1
            System.out.println(questionnaire.getDel());
            return true; // 删除成功
        } else {
            return false; // 删除失败
        }
    }


    // 修改问卷信息
    public int updatequestionnaire(QuestionnaireEntity QuestionnaireEntity) {
        int updateResult = questionnaireEntityMapper.updateByPrimaryKeySelective(QuestionnaireEntity);
        return updateResult;
    }

    public List<QuestionnaireEntity> selectQuestionByProId(String id) {
        System.out.println(id);
        return questionnaireEntityMapper.selectQuestionByProId(id);
    }

    public QuestionnaireEntity editQuestionById(String id) {
        return questionnaireEntityMapper.editQuestionById(id);
    }

    //发布
    public void publishQuestionnaire(Long questionnaireId) {
        // 生成唯一链接的逻辑，可以使用UUID或者其他方式生成
        String uniqueLink = generateUniqueLink(questionnaireId);
        // 更新问卷状态和链接
        questionnaireEntityMapper.updateStatusAndLink(questionnaireId, "已发布", uniqueLink);
    }

    public String generateUniqueLink(Long questionnaireId) {
        // 生成唯一链接的具体实现逻辑
        return UUID.randomUUID().toString();
    }
    //问卷预览
    public String getQuestionnairePreviewLink(Long questionnaireId) {
        String uniqueLink = generateUniqueLink(questionnaireId);
        return uniqueLink;
    }
    public String getLink(QuestionnaireEntity questionNaireEntity){
        return questionnaireEntityMapper.getLink(questionNaireEntity);
    }
    public int updateLink(QuestionnaireEntity questionNaireEntity,String link){
        return questionnaireEntityMapper.updateLink(questionNaireEntity,link);
    }

}
