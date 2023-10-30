package com.sisp.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class AnalysisList {
    private List<AnalysisVO> list = new ArrayList();

    public AnalysisList(List<AnalysisVO> list) {
        this.list = list;
    }

    public List<AnalysisVO> getList() {
        return list;
    }

    public void setList(List<AnalysisVO> list) {
        this.list = list;
    }

    public AnalysisList() {
    }
}
