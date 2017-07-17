package com.chenshun.utils.orm.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/6/16 17:38  <p />
 * Version: V1.0  <p />
 * Description: 审计信息实体 <p />
 */
public class AuditInfoEntity {

    /* 创建人PIN */
    protected String createBy;

    /* 创建时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    /* 修改人PIN */
    protected String modifyBy;

    /* 修改时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Date modifyTime;

    @JSONField(serialize = false, deserialize = true)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JSONField(serialize = false, deserialize = true)
    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}
