package com.sisp;
import com.sisp.dao.AnswerEntityMapper;
import com.sisp.service.AnswerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
public class AnswerServiceTest {

    @Mock
    private AnswerEntityMapper answerEntityMapper;

    @InjectMocks
    private AnswerService answerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetQuestionById() {
        // 设置测试数据
        String id = "123";
        List<Map<String, Object>> expectedList = new ArrayList<>();
        // 添加测试数据到expectedList

        // 设置mock行为
        when(answerEntityMapper.getQuestionById(id)).thenReturn(expectedList);

        // 调用被测试方法
        List<Map<String, Object>> resultList = answerService.getQuestionById(id);

        // 验证结果
        assertEquals(expectedList, resultList);

        // 验证方法是否被调用
        verify(answerEntityMapper, times(1)).getQuestionById(id);
    }

    @Test
    public void testGetOptionById() {
        // 设置测试数据
        Object questionId = "123";
        List<Map<String, Object>> expectedList = new ArrayList<>();
        // 添加测试数据到expectedList

        // 设置mock行为
        when(answerEntityMapper.getOptionById(questionId)).thenReturn(expectedList);

        // 调用被测试方法
        List<Map<String, Object>> resultList = answerService.getOptionById(questionId);

        // 验证结果
        assertEquals(expectedList, resultList);

        // 验证方法是否被调用
        verify(answerEntityMapper, times(1)).getOptionById(questionId);
    }
}

