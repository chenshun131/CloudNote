package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class NoteStatus implements Serializable {

    private String cnNoteStatusId;

    private String cnNoteStatusCode;

    private String cnNoteStatusName;

    public String getCnNoteStatusId() {
        return cnNoteStatusId;
    }

    public void setCnNoteStatusId(String cnNoteStatusId) {
        this.cnNoteStatusId = cnNoteStatusId == null ? null : cnNoteStatusId.trim();
    }

    public String getCnNoteStatusCode() {
        return cnNoteStatusCode;
    }

    public void setCnNoteStatusCode(String cnNoteStatusCode) {
        this.cnNoteStatusCode = cnNoteStatusCode == null ? null : cnNoteStatusCode.trim();
    }

    public String getCnNoteStatusName() {
        return cnNoteStatusName;
    }

    public void setCnNoteStatusName(String cnNoteStatusName) {
        this.cnNoteStatusName = cnNoteStatusName == null ? null : cnNoteStatusName.trim();
    }

}
