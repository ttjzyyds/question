package com.sisp.dao;

import com.sisp.dao.entity.AnalysisVO;
import com.sisp.dao.entity.AnswerOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 11:00 上午
 * @Version 1.0
 */
@Repository
@Mapper
@Component
public interface AnswerOptionEntityMapper {

    List<AnswerOption> selectListById(Integer id);

    List<AnalysisVO> analysis(@Param("id") String id, @Param("i") int i);

    List<AnalysisVO> analysisByType(@Param("question_id") String question_id, @Param("type") String type);
}
