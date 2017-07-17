package com.chenshun.studyapp.domain;

import java.io.Serializable;

public class User implements Serializable {

    /** 用户ID */
    private String cnUserId;

    /** 用户名 */
    private String cnUserName;

    /** 密码 */
    private String cnUserPassword;

    /** 令牌 */
    private String cnUserToken;

    /** 说明 */
    private String cnUserDesc;

    public String getCnUserId() {
        return cnUserId;
    }

    public void setCnUserId(String cnUserId) {
        this.cnUserId = cnUserId == null ? null : cnUserId.trim();
    }

    public String getCnUserName() {
        return cnUserName;
    }

    public void setCnUserName(String cnUserName) {
        this.cnUserName = cnUserName == null ? null : cnUserName.trim();
    }

    public String getCnUserPassword() {
        return cnUserPassword;
    }

    public void setCnUserPassword(String cnUserPassword) {
        this.cnUserPassword = cnUserPassword == null ? null : cnUserPassword.trim();
    }

    public String getCnUserToken() {
        return cnUserToken;
    }

    public void setCnUserToken(String cnUserToken) {
        this.cnUserToken = cnUserToken == null ? null : cnUserToken.trim();
    }

    public String getCnUserDesc() {
        return cnUserDesc;
    }

    public void setCnUserDesc(String cnUserDesc) {
        this.cnUserDesc = cnUserDesc == null ? null : cnUserDesc.trim();
    }

}
