package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class Note implements Serializable {

    private String cnNoteId;

    private String cnNotebookId;

    private String cnUserId;

    /** 1:正常 2:删除 3:收藏 */
    private String cnNoteStatusId;

    private String cnNoteTypeId;

    private String cnNoteTitle;

    private Long cnNoteCreateTime;

    private Long cnNoteLastModifyTime;

    private String cnNoteBody;

    public String getCnNoteId() {
        return cnNoteId;
    }

    public void setCnNoteId(String cnNoteId) {
        this.cnNoteId = cnNoteId == null ? null : cnNoteId.trim();
    }

    public String getCnNotebookId() {
        return cnNotebookId;
    }

    public void setCnNotebookId(String cnNotebookId) {
        this.cnNotebookId = cnNotebookId == null ? null : cnNotebookId.trim();
    }

    public String getCnUserId() {
        return cnUserId;
    }

    public void setCnUserId(String cnUserId) {
        this.cnUserId = cnUserId == null ? null : cnUserId.trim();
    }

    public String getCnNoteStatusId() {
        return cnNoteStatusId;
    }

    public void setCnNoteStatusId(String cnNoteStatusId) {
        this.cnNoteStatusId = cnNoteStatusId == null ? null : cnNoteStatusId.trim();
    }

    public String getCnNoteTypeId() {
        return cnNoteTypeId;
    }

    public void setCnNoteTypeId(String cnNoteTypeId) {
        this.cnNoteTypeId = cnNoteTypeId == null ? null : cnNoteTypeId.trim();
    }

    public String getCnNoteTitle() {
        return cnNoteTitle;
    }

    public void setCnNoteTitle(String cnNoteTitle) {
        this.cnNoteTitle = cnNoteTitle == null ? null : cnNoteTitle.trim();
    }

    public Long getCnNoteCreateTime() {
        return cnNoteCreateTime;
    }

    public void setCnNoteCreateTime(Long cnNoteCreateTime) {
        this.cnNoteCreateTime = cnNoteCreateTime;
    }

    public Long getCnNoteLastModifyTime() {
        return cnNoteLastModifyTime;
    }

    public void setCnNoteLastModifyTime(Long cnNoteLastModifyTime) {
        this.cnNoteLastModifyTime = cnNoteLastModifyTime;
    }

    public String getCnNoteBody() {
        return cnNoteBody;
    }

    public void setCnNoteBody(String cnNoteBody) {
        this.cnNoteBody = cnNoteBody == null ? null : cnNoteBody.trim();
    }

}
