package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class NoteType implements Serializable {

    private String cnNoteTypeId;

    private String cnNoteTypeCode;

    private String cnNoteTypeName;

    private String cnNoteTypeDesc;

    public String getCnNoteTypeId() {
        return cnNoteTypeId;
    }

    public void setCnNoteTypeId(String cnNoteTypeId) {
        this.cnNoteTypeId = cnNoteTypeId == null ? null : cnNoteTypeId.trim();
    }

    public String getCnNoteTypeCode() {
        return cnNoteTypeCode;
    }

    public void setCnNoteTypeCode(String cnNoteTypeCode) {
        this.cnNoteTypeCode = cnNoteTypeCode == null ? null : cnNoteTypeCode.trim();
    }

    public String getCnNoteTypeName() {
        return cnNoteTypeName;
    }

    public void setCnNoteTypeName(String cnNoteTypeName) {
        this.cnNoteTypeName = cnNoteTypeName == null ? null : cnNoteTypeName.trim();
    }

    public String getCnNoteTypeDesc() {
        return cnNoteTypeDesc;
    }

    public void setCnNoteTypeDesc(String cnNoteTypeDesc) {
        this.cnNoteTypeDesc = cnNoteTypeDesc == null ? null : cnNoteTypeDesc.trim();
    }

}
