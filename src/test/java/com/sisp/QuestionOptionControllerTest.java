package com.sisp;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.controller.QuestionOptionController;
import com.sisp.dao.entity.QuestionOption;
import com.sisp.service.QuestionOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionOptionControllerTest {

    @Mock
    private QuestionOptionService questionOptionService;

    @InjectMocks
    private QuestionOptionController questionOptionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddQuestionOptionSuccess() {
        // Arrange
        QuestionOption questionOption = new QuestionOption();
        when(questionOptionService.addQuestionOption(questionOption)).thenReturn("success");

        // Act
        HttpResponseEntity httpResponseEntity = questionOptionController.addQuestionOption(questionOption);

        // Assert
        assertEquals("666", httpResponseEntity.getCode());
        assertEquals("新增成功!", httpResponseEntity.getMessage());
    }

    @Test
    public void testAddQuestionOptionFailure() {
        // Arrange
        QuestionOption questionOption = new QuestionOption();
        when(questionOptionService.addQuestionOption(questionOption)).thenReturn("");

        // Act
        HttpResponseEntity httpResponseEntity = questionOptionController.addQuestionOption(questionOption);

        // Assert
        assertEquals("0", httpResponseEntity.getCode());
        assertEquals("新增失败!", httpResponseEntity.getMessage());
    }


    @Test
    void testUpdateQuestionOption() {
        QuestionOption questionOption = new QuestionOption();
        questionOption.setId("123");

        when(questionOptionService.updateQuestionOption(any(QuestionOption.class))).thenReturn(true);

        HttpResponseEntity expectedResponse = new HttpResponseEntity();
        expectedResponse.setCode("666");
        expectedResponse.setMessage("修改成功!");

        HttpResponseEntity actualResponse = questionOptionController.addQuestionOption(questionOption);

        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }
}

