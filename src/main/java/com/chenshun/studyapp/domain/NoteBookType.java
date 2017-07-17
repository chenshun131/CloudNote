package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class NoteBookType implements Serializable {

    private String cnNotebookTypeId;

    private String cnNotebookTypeCode;

    private String cnNotebookTypeName;

    private String cnNotebookTypeDesc;

    public String getCnNotebookTypeId() {
        return cnNotebookTypeId;
    }

    public void setCnNotebookTypeId(String cnNotebookTypeId) {
        this.cnNotebookTypeId = cnNotebookTypeId == null ? null : cnNotebookTypeId.trim();
    }

    public String getCnNotebookTypeCode() {
        return cnNotebookTypeCode;
    }

    public void setCnNotebookTypeCode(String cnNotebookTypeCode) {
        this.cnNotebookTypeCode = cnNotebookTypeCode == null ? null : cnNotebookTypeCode.trim();
    }

    public String getCnNotebookTypeName() {
        return cnNotebookTypeName;
    }

    public void setCnNotebookTypeName(String cnNotebookTypeName) {
        this.cnNotebookTypeName = cnNotebookTypeName == null ? null : cnNotebookTypeName.trim();
    }

    public String getCnNotebookTypeDesc() {
        return cnNotebookTypeDesc;
    }

    public void setCnNotebookTypeDesc(String cnNotebookTypeDesc) {
        this.cnNotebookTypeDesc = cnNotebookTypeDesc == null ? null : cnNotebookTypeDesc.trim();
    }

}
