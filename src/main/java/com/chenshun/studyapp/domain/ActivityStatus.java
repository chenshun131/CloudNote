package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class ActivityStatus implements Serializable {

    private String cnActivityStatusId;

    private String cnActivityId;

    private String cnActivityStatusCode;

    private String cnActivityStatusName;

    public String getCnActivityStatusId() {
        return cnActivityStatusId;
    }

    public void setCnActivityStatusId(String cnActivityStatusId) {
        this.cnActivityStatusId = cnActivityStatusId == null ? null : cnActivityStatusId.trim();
    }

    public String getCnActivityId() {
        return cnActivityId;
    }

    public void setCnActivityId(String cnActivityId) {
        this.cnActivityId = cnActivityId == null ? null : cnActivityId.trim();
    }

    public String getCnActivityStatusCode() {
        return cnActivityStatusCode;
    }

    public void setCnActivityStatusCode(String cnActivityStatusCode) {
        this.cnActivityStatusCode = cnActivityStatusCode == null ? null : cnActivityStatusCode.trim();
    }

    public String getCnActivityStatusName() {
        return cnActivityStatusName;
    }

    public void setCnActivityStatusName(String cnActivityStatusName) {
        this.cnActivityStatusName = cnActivityStatusName == null ? null : cnActivityStatusName.trim();
    }

}
