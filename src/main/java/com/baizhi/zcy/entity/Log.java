package com.baizhi.zcy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


public class Log implements Serializable {

    private String id;
    private String adminName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date times;
    private String description;
    private String status;

    public Log(String id, String adminName, Date times, String description, String status) {
        this.id = id;
        this.adminName = adminName;
        this.times = times;
        this.description = description;
        this.status = status;
    }

    public Log() {
    }

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", adminName='" + adminName + '\'' +
                ", times=" + times +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
