package com.sisp;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.controller.SysResult;
import com.sisp.dao.entity.OptionAndQuestVo;
import com.sisp.dao.entity.ProjectEntity;
import com.sisp.controller.ProjectController;
import com.sisp.dao.entity.QuestionInfo;
import com.sisp.dao.entity.QuestionOption;
import com.sisp.service.AnswerService;
import com.sisp.service.ProjectService;
import com.sisp.service.QuestionOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private AnswerService answerService;

    @Mock
    private QuestionOptionService questionOptionService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueryProjectList() {
        // Arrange
        ProjectEntity projectEntity = new ProjectEntity();
        List<ProjectEntity> results = new ArrayList<>();
        results.add(new ProjectEntity());
        when(projectService.queryProjectList(projectEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = projectController.queryProjectList(projectEntity);

        // Assert
        assertEquals("666", httpResponseEntity.getCode());
        assertEquals(results.get(0), httpResponseEntity.getData());
    }


    @Test
    public void testQueryProjectListEmpty() {
        // Arrange
        ProjectEntity projectEntity = new ProjectEntity();
        List<ProjectEntity> results = new ArrayList<>();
        when(projectService.queryProjectList(projectEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = projectController.queryProjectList(projectEntity);

        // Assert
        assertEquals("1", httpResponseEntity.getCode());
        assertEquals("No results found", httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }


    @Test
    public void testQueryProjectListException() {
        // Arrange
        ProjectEntity projectEntity = new ProjectEntity();
        when(projectService.queryProjectList(projectEntity)).thenThrow(new RuntimeException());

        // Act
        HttpResponseEntity httpResponseEntity = projectController.queryProjectList(projectEntity);

        // Assert
        assertEquals(null, httpResponseEntity.getCode());
        assertEquals(null, httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }

    @Test
    public void testGetQuestionById() {
        // 模拟数据
        String questionId = "123";
        List<Map<String, Object>> questionList = new ArrayList<>();

        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("id", questionId);

        List<Map<String, Object>> optionList = new ArrayList<>();
        Map<String, Object> optionMap = new HashMap<>();
        optionMap.put("id", "1");
        optionMap.put("text", "Option 1");
        optionList.add(optionMap);

        questionMap.put("listByOption", optionList);
        questionList.add(questionMap);

        // 设置模拟对象的行为
        when(answerService.getQuestionById(questionId)).thenReturn(questionList);
        when(answerService.getOptionById(questionId)).thenReturn(optionList);

        // 调用被测试的方法
        SysResult result = projectController.getQuestionById(questionId);

        // 打印数据以进行验证
        System.out.println("Question List:");
        for (Map<String, Object> question : questionList) {
            System.out.println(question);
        }

        // 断言返回结果
        assertEquals(SysResult.success().setData(questionList), result);
    }


}