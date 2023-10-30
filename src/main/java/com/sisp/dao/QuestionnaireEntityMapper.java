package com.sisp.dao;

        import com.sisp.dao.entity.QuestionnaireEntity;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;
        import org.springframework.stereotype.Component;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
@Mapper
@Component
public interface QuestionnaireEntityMapper {
    // 查询问卷列表
    List<QuestionnaireEntity> queryQuestionnaireList(QuestionnaireEntity QuestionnaireEntity);

    // 新增问卷
    int insert(QuestionnaireEntity QuestionnaireEntity);

    // 修改问卷
    int updateByPrimaryKeySelective(QuestionnaireEntity QuestionnaireEntity);

    // 根据ID查询问卷
    QuestionnaireEntity selectQuestionById(String id);

    //根据项目id查询问卷列表
    List<QuestionnaireEntity> selectQuestionByProId(String id);

    //删除问卷
    int markQuestionnaireAsDeleted(String id);

    QuestionnaireEntity editQuestionById(String id);

    void  updateStatusAndLink(@Param("questionnaireId") Long questionnaireId,
                              @Param("status") String status,
                              @Param("link") String link);

    String getLink(QuestionnaireEntity questionNaireEntity);
    int updateLink(@Param("questionnaire") QuestionnaireEntity questionNaireEntity, @Param("link") String link);
}
