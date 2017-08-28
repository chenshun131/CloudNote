package com.chenshun.studyapp.jms.common;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/8/25 13:46  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class CustData implements Serializable {

    private static final long serialVersionUID = -275795510050620920L;

    private long custId;

    private String custName;

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

}
