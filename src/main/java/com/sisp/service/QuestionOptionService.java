package com.sisp.service;

        import com.sisp.controller.OptionVO;
        import com.sisp.dao.QuestionOptionEntityMapper;
        import com.sisp.dao.entity.QuestionOption;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.UUID;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/22 9:57 下午
 * @Version 1.0
 */
@Service
public class QuestionOptionService {

    @Autowired
    private QuestionOptionEntityMapper questionOptionEntityMapper;

    public String addQuestionOption(QuestionOption questionOption) {
//        //每个问卷只能设计一次
//        if (selectCountByQuestionId(questionOption.getQuestion_id()) > 1) {
//            return false;
//        }
        String s = UUID.randomUUID().toString();
        questionOption.setId(s);
        questionOptionEntityMapper.addQuestionOption(questionOption);
        return s;
    }


    public int selectCountByQuestionId(String question_id) {
        return questionOptionEntityMapper.selectCountByQuestionId(question_id);
    }

    public QuestionOption selectQuestionOptionById(String id) {
        return questionOptionEntityMapper.selectQuestionOptionById(id);
    }

    public boolean updateQuestionOption(QuestionOption questionOption) {
        return questionOptionEntityMapper.updateQuestionOption(questionOption);
    }

    public List<OptionVO> selectQuestionOptionList() {
        return questionOptionEntityMapper.selectQuestionOptionList();
    }

    public void delete(String id) {
        questionOptionEntityMapper.delete(id);
    }
}
