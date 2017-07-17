package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class NoteActivity implements Serializable {

    private String cnNoteActivityId;

    private String cnActivityId;

    private String cnNoteId;

    private Integer cnNoteActivityUp;

    private Integer cnNoteActivityDown;

    private String cnNoteActivityTitle;

    private String cnNoteActivityBody;

    public String getCnNoteActivityId() {
        return cnNoteActivityId;
    }

    public void setCnNoteActivityId(String cnNoteActivityId) {
        this.cnNoteActivityId = cnNoteActivityId == null ? null : cnNoteActivityId.trim();
    }

    public String getCnActivityId() {
        return cnActivityId;
    }

    public void setCnActivityId(String cnActivityId) {
        this.cnActivityId = cnActivityId == null ? null : cnActivityId.trim();
    }

    public String getCnNoteId() {
        return cnNoteId;
    }

    public void setCnNoteId(String cnNoteId) {
        this.cnNoteId = cnNoteId == null ? null : cnNoteId.trim();
    }

    public Integer getCnNoteActivityUp() {
        return cnNoteActivityUp;
    }

    public void setCnNoteActivityUp(Integer cnNoteActivityUp) {
        this.cnNoteActivityUp = cnNoteActivityUp;
    }

    public Integer getCnNoteActivityDown() {
        return cnNoteActivityDown;
    }

    public void setCnNoteActivityDown(Integer cnNoteActivityDown) {
        this.cnNoteActivityDown = cnNoteActivityDown;
    }

    public String getCnNoteActivityTitle() {
        return cnNoteActivityTitle;
    }

    public void setCnNoteActivityTitle(String cnNoteActivityTitle) {
        this.cnNoteActivityTitle = cnNoteActivityTitle == null ? null : cnNoteActivityTitle.trim();
    }

    public String getCnNoteActivityBody() {
        return cnNoteActivityBody;
    }

    public void setCnNoteActivityBody(String cnNoteActivityBody) {
        this.cnNoteActivityBody = cnNoteActivityBody == null ? null : cnNoteActivityBody.trim();
    }

}
