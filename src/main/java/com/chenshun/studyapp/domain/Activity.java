package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class Activity implements Serializable {

    private String cnActivityId;

    private String cnActivityTitle;

    private Long cnActivityEndTime;

    private String cnActivityBody;

    public String getCnActivityId() {
        return cnActivityId;
    }

    public void setCnActivityId(String cnActivityId) {
        this.cnActivityId = cnActivityId == null ? null : cnActivityId.trim();
    }

    public String getCnActivityTitle() {
        return cnActivityTitle;
    }

    public void setCnActivityTitle(String cnActivityTitle) {
        this.cnActivityTitle = cnActivityTitle == null ? null : cnActivityTitle.trim();
    }

    public Long getCnActivityEndTime() {
        return cnActivityEndTime;
    }

    public void setCnActivityEndTime(Long cnActivityEndTime) {
        this.cnActivityEndTime = cnActivityEndTime;
    }

    public String getCnActivityBody() {
        return cnActivityBody;
    }

    public void setCnActivityBody(String cnActivityBody) {
        this.cnActivityBody = cnActivityBody == null ? null : cnActivityBody.trim();
    }

}
