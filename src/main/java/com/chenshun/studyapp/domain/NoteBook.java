package com.chenshun.studyapp.domain;

import java.io.Serializable;
import java.util.Date;

public class NoteBook implements Serializable {

    /** 笔记本ID */
    private String cnNotebookId;

    /** 用户ID */
    private String cnUserId;

    /** 笔记本类型 */
    private String cnNotebookTypeId;

    /** 笔记本名 */
    private String cnNotebookName;

    private Date cnNotebookCreatetime;

    /** 笔记本描述 */
    private String cnNotebookDesc;

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

    public String getCnNotebookTypeId() {
        return cnNotebookTypeId;
    }

    public void setCnNotebookTypeId(String cnNotebookTypeId) {
        this.cnNotebookTypeId = cnNotebookTypeId == null ? null : cnNotebookTypeId.trim();
    }

    public String getCnNotebookName() {
        return cnNotebookName;
    }

    public void setCnNotebookName(String cnNotebookName) {
        this.cnNotebookName = cnNotebookName == null ? null : cnNotebookName.trim();
    }

    public Date getCnNotebookCreatetime() {
        return cnNotebookCreatetime;
    }

    public void setCnNotebookCreatetime(Date cnNotebookCreatetime) {
        this.cnNotebookCreatetime = cnNotebookCreatetime;
    }

    public String getCnNotebookDesc() {
        return cnNotebookDesc;
    }

    public void setCnNotebookDesc(String cnNotebookDesc) {
        this.cnNotebookDesc = cnNotebookDesc == null ? null : cnNotebookDesc.trim();
    }

}
