package com.sisp;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.controller.QuestionOptionController;
import com.sisp.controller.questionnaireController;
import com.sisp.dao.QuestionnaireEntityMapper;
import com.sisp.dao.entity.QuestionnaireEntity;
import com.sisp.service.QuestionnaireService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionnaireTests{

    @Mock
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    @InjectMocks
    private QuestionnaireService questionnaireService;

    @Autowired
    com.sisp.controller.questionnaireController questionnaireController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteQuestionnaireById_Success() {
        // 设置测试数据
        String id = "123";
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setStatus("未发布");

        // 设置mock行为
        when(questionnaireEntityMapper.selectQuestionById(id)).thenReturn(questionnaireEntity);

        // 调用被测试方法
        boolean result = questionnaireService.deleteQuestionnaireById(id);

        // 验证结果
        assertEquals(true, result);

        // 验证方法是否被调用
        verify(questionnaireEntityMapper, times(1)).selectQuestionById(id);
        verify(questionnaireEntityMapper, times(1)).markQuestionnaireAsDeleted(id);
    }

    @Test
    public void testDeleteQuestionnaireById_Failure() {
        // 设置测试数据
        String id = "123";
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setStatus("已发布");

        // 设置mock行为
        when(questionnaireEntityMapper.selectQuestionById(id)).thenReturn(questionnaireEntity);

        // 调用被测试方法
        boolean result = questionnaireService.deleteQuestionnaireById(id);
        System.out.println(result);

        // 验证结果
        assertEquals(false, result);

        // 验证方法是否被调用
        verify(questionnaireEntityMapper, times(1)).selectQuestionById(id);
        verify(questionnaireEntityMapper, never()).markQuestionnaireAsDeleted(id);
    }
}
