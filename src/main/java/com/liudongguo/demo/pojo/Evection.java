package com.liudongguo.demo.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liudongguo
 * @title: Evection
 * @projectName activiti-demo
 * @description: TODO
 * @date 2021/5/13下午8:41
 */
public class Evection implements Serializable {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 出差单的名字
     */
    private String evectionName;
    /**
     * 出差天数
     */
    private Double num;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 目的地
     */
    private String destination;
    /**
     * 理由
     */
    private String reson;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvectionName() {
        return evectionName;
    }

    public void setEvectionName(String evectionName) {
        this.evectionName = evectionName;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }
}
