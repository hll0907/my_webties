package com.hll.web.pojo;

import java.util.Date;

public class IntergralRecords {
    private Integer id;

    private Integer userId;

    private Integer tempIntergral;

    private Integer intergral;

    private Date createdAt;

    private Date updatedAt;

    private String note;

    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTempIntergral() {
        return tempIntergral;
    }

    public void setTempIntergral(Integer tempIntergral) {
        this.tempIntergral = tempIntergral;
    }

    public Integer getIntergral() {
        return intergral;
    }

    public void setIntergral(Integer intergral) {
        this.intergral = intergral;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}