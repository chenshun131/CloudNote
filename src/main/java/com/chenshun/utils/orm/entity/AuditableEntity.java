package com.chenshun.utils.orm.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/6/16 17:31  <p />
 * Version: V1.0  <p />
 * Description: 含审计信息的Entity基类. <p />
 */
@MappedSuperclass
public class AuditableEntity extends BaseEntity {

    protected Date createTime;

    protected String createBy;

    protected Date lastModifyTime;

    protected String lastModifyBy;

    /**
     * 创建时间.
     */
    // 本属性只在save时有效,update时无效.
    @Column(updatable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建的操作员的登录名.
     */
    @Column(updatable = false, length = 32)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 最后修改时间.
     */
    // 本属性只在update时有效,save时无效.
    @Column(insertable = false)
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * 最后修改的操作员的登录名.
     */
    @Column(insertable = false, length = 32)
    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

}
