package com.chenshun.studyapp.entity;

import java.sql.Date;
import java.util.Calendar;

/**
 * User: mew <p />
 * Time: 17/7/15 15:09  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SearchNote {

    /** 标题 */
    private String title;

    /** 状态 */
    private String status;

    private String beginDate;//input_html

    private String endDate;//input_html

    /**
     * 在SQL中#{beginTime}获取
     *
     * @return
     */
    public Long getBeginTime() {
        if (beginDate != null && !"".equals(beginDate)) {
            return Date.valueOf(beginDate).getTime();
        } else {
            return null;
        }
    }

    /**
     * 在SQL中#{endTime}获取
     *
     * @return
     */
    public Long getEndTime() {
        if (endDate != null && !"".equals(endDate)) {
            Date date = Date.valueOf(endDate);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DAY_OF_MONTH, 1);
            return c1.getTimeInMillis();
        } else {
            return null;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
