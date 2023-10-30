package com.sisp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sisp.controller.AnswerController;
import com.sisp.controller.AnswerVo;
import com.sisp.controller.QuestionVO;
import com.sisp.dao.entity.AnalysisVO;
import com.sisp.dao.entity.Answer;
import com.sisp.dao.entity.AnswerOption;
import com.sisp.dao.entity.QuestionOption;
import com.sisp.service.AnswerOptionService;
import com.sisp.service.AnswerService;
import com.sisp.service.QuestionOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
class AnswerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AnswerService answerService;

    @Mock
    private QuestionOptionService questionOptionService;

    @Mock
    private AnswerOptionService answerOptionService;

    @InjectMocks
    private AnswerController answerController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
    }

    @Test
    void testSelectAnswerList() throws Exception {
        // Prepare test data
        List<Answer> answerList = new ArrayList<>();
        // Add answer objects to the list

        // Mock the service method
        when(answerService.selectAnswerList()).thenReturn(answerList);

        // Perform the MVC request and assertions
        mockMvc.perform(post("/answer/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(answerList.size())))
                .andDo(print()); // Print request and response details

        // Verify the service method was called
        verify(answerService).selectAnswerList();
    }

    @Test
    void testGetAnswerInfo() throws Exception {
        // Prepare test data
        AnswerVo answerVo = new AnswerVo();
        // Set properties of answerVo

        String questionId = "123";
        QuestionOption questionOption = new QuestionOption();
        // Set properties of questionOption

        List<AnswerOption> answerOptions = new ArrayList<>();
        // Add answerOption objects to the list

        // Mock the service methods
        when(answerService.selectQuestionIdById(any())).thenReturn(questionId);
        when(questionOptionService.selectQuestionOptionById(any())).thenReturn(questionOption);
        when(answerOptionService.selectListById(any())).thenReturn(answerOptions);

        // Perform the MVC request and assertions
        mockMvc.perform(post("/answer/info")
                .contentType("application/json")
                .content(asJsonString(answerVo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionOption", notNullValue()))
                .andExpect(jsonPath("$.list", hasSize(answerOptions.size())))
                .andDo(print()); // Print request and response details to console

        // Verify the service methods were called
        verify(answerService).selectQuestionIdById(any());
        verify(questionOptionService).selectQuestionOptionById(any());
        verify(answerOptionService).selectListById(any());
    }

    @Test
    void testAnalysis() throws Exception {
        // Prepare test data
        QuestionVO questionVO = new QuestionVO();
        questionVO.setId("c1782c4dd09f41bb97d1be0f52288374");

        Answer answer = new Answer();
        answer.setQuestion_id("c1782c4dd09f41bb97d1be0f52288374");
        answer.setCount(3);

        List<AnalysisVO> analysisList1 = new ArrayList<>();
        // Add analysisVO objects to the analysisList1

        List<AnalysisVO> analysisList2 = new ArrayList<>();
        // Add analysisVO objects to the analysisList2

        List<List<AnalysisVO>> expectedAnalysisResult = new ArrayList<>();
        expectedAnalysisResult.add(analysisList1);
        expectedAnalysisResult.add(analysisList2);

        // Mock the service methods
        when(answerService.selectAnswerByQuestionId(anyString())).thenReturn(answer);
        when(answerOptionService.analysis(anyString(), anyInt())).thenReturn(analysisList1);
        when(answerOptionService.analysisByType(anyString(), eq("单选"))).thenReturn(analysisList1);
        when(answerOptionService.analysisByType(anyString(), eq("多选"))).thenReturn(analysisList2);

        // Perform the MVC request and assertions
        mockMvc.perform(MockMvcRequestBuilders.post("/answer/analysis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(questionVO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].length()").value(analysisList1.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].length()").value(analysisList2.size()));

        // Verify the service methods were called
        verify(answerService).selectAnswerByQuestionId(anyString());
        verify(answerOptionService, times(answer.getCount())).analysis(anyString(), anyInt());
        verify(answerOptionService).analysisByType(anyString(), eq("单选"));
        verify(answerOptionService).analysisByType(anyString(), eq("多选"));
    }

    // Utility method to convert an object to JSON string
    private static String asJsonString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
