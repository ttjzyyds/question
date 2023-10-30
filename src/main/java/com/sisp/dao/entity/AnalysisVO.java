package com.sisp.dao.entity;

import java.io.Serializable;

/**
 * @Author: acton_zhang
 * @Date: 2023/6/23 11:41 上午
 * @Version 1.0
 */
public class AnalysisVO implements Serializable {

    private String type;

    private Integer count;

    public AnalysisVO(String type, Integer count) {
        this.type = type;
        this.count = count;
    }

    public AnalysisVO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AnalysisVO{" +
                "type='" + type + '\'' +
                ", count=" + count +
                '}';
    }
}
