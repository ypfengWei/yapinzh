package cn.yapin.gzh.entity;

import java.io.Serializable;

public class Census implements Serializable {
    private Double turnover;
    private Integer totalNumber;

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }
}
