package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class Share implements Serializable {

    private String cnShareId;

    private String cnShareTitle;

    private String cnNoteId;

    private String cnShareBody;

    public String getCnShareId() {
        return cnShareId;
    }

    public void setCnShareId(String cnShareId) {
        this.cnShareId = cnShareId == null ? null : cnShareId.trim();
    }

    public String getCnShareTitle() {
        return cnShareTitle;
    }

    public void setCnShareTitle(String cnShareTitle) {
        this.cnShareTitle = cnShareTitle == null ? null : cnShareTitle.trim();
    }

    public String getCnNoteId() {
        return cnNoteId;
    }

    public void setCnNoteId(String cnNoteId) {
        this.cnNoteId = cnNoteId == null ? null : cnNoteId.trim();
    }

    public String getCnShareBody() {
        return cnShareBody;
    }

    public void setCnShareBody(String cnShareBody) {
        this.cnShareBody = cnShareBody == null ? null : cnShareBody.trim();
    }

}
