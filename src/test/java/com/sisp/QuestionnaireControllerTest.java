package com.sisp;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.dao.entity.QuestionnaireEntity;
import com.sisp.service.QuestionnaireService;
import com.sisp.controller.questionnaireController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionnaireControllerTest {

    @Mock
    private QuestionnaireService questionnaireService;

    @InjectMocks
    private questionnaireController questionnaireController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueryQuestionnaireList() {
        // Arrange
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        List<QuestionnaireEntity> results = new ArrayList<>();
        results.add(new QuestionnaireEntity());
        when(questionnaireService.queryquestionnaireList(questionnaireEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = questionnaireController.queryquestionnaireList(questionnaireEntity);

        // Assert
        assertEquals("666", httpResponseEntity.getCode());
        assertEquals("查询成功", httpResponseEntity.getMessage());
        assertEquals(results, httpResponseEntity.getData());
    }

    @Test
    public void testQueryQuestionnaireListEmpty() {
        // Arrange
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        List<QuestionnaireEntity> results = new ArrayList<>();
        when(questionnaireService.queryquestionnaireList(questionnaireEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = questionnaireController.queryquestionnaireList(questionnaireEntity);

        // Assert
        assertEquals("0", httpResponseEntity.getCode());
        assertEquals(null, httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }

    @Test
    public void testQueryQuestionnaireListException() {
        // Arrange
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        when(questionnaireService.queryquestionnaireList(questionnaireEntity)).thenThrow(new RuntimeException());

        // Act
        HttpResponseEntity httpResponseEntity = questionnaireController.queryquestionnaireList(questionnaireEntity);

        // Assert
        assertEquals(null, httpResponseEntity.getCode());
        assertEquals(null, httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }
}