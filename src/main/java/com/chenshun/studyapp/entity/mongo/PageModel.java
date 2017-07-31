package com.chenshun.studyapp.entity.mongo;

import java.io.Serializable;
import java.util.List;

/**
 * User: mew <p />
 * Time: 17/7/31 14:01  <p />
 * Version: V1.0  <p />
 * Description: 分页 <p />
 */
public class PageModel<T> implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 默认页码 */
    public static final int DEFAULT_PAGENO = 1;

    /** 默认页容量 */
    public static final int DEFAULT_PAGESIZE = 20;

    /** 基础字段 */

    /** 当前页码 */
    private int pageNo;

    /** 页容量 */
    private int pageSize;

    /** 总记录 */
    private int totalCount;

    /** 总页数 */
    private int totalPage;

    /** 数据集合 */
    private List<T> list;

    /** 构造方法 */

    /**
     * 默认，初始化pageNo=1，pageSize=20
     */
    public PageModel() {
        // 一定要先设置页容量
        this.setPageSize(DEFAULT_PAGESIZE);
        this.setPageNo(DEFAULT_PAGENO);
    }

    /**
     * 带有页码，页容量参数的构造函数
     *
     * @param pageNo
     * @param pageSize
     */
    public PageModel(int pageNo, int pageSize) {
        // 一定要先设置页容量
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
    }

    /**
     * 带有页码，页容量，总记录，数据集合参数的构造函数
     *
     * @param pageNo
     *         The current pageNo
     * @param pageSize
     *         The page's size
     * @param totalCount
     *         The total record count
     * @param list
     *         The record list
     */
    public PageModel(int pageNo, int pageSize, int totalCount, List<T> list) {
        // 一定要先设置页容量
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
        this.setTotalCount(totalCount);
        this.setList(list);
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo <= 0) {
            this.pageNo = DEFAULT_PAGENO;
        } else {
            this.pageNo = pageNo;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_PAGESIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount <= 0) {
            this.totalCount = 0;
        } else {
            this.totalCount = totalCount;
        }
    }

    public int getTotalPage() {
        this.totalPage = (getTotalCount() - 1) / getPageSize() + 1;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        if (totalPage < 0) {
            this.totalPage = 0;
        } else {
            this.totalPage = totalPage;
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 是否是第一页
     *
     * @return 是否
     */
    public boolean isFirstPage() {
        return this.pageNo <= 1;
    }

    /**
     * 是否是最后一页
     *
     * @return 是否
     */
    public boolean isLastPage() {

        return this.pageNo >= this.getTotalPage();
    }

    /**
     * 下一页
     *
     * @return 下一页页码
     */
    public int getNextPage() {
        if (this.isLastPage()) {
            return this.getTotalPage();
        }
        return this.pageNo + 1;
    }

    /**
     * 上一页
     *
     * @return 上一页页码
     */
    public int getPrevPage() {
        if (this.pageNo <= 1) {
            return 1;
        }
        return this.pageNo - 1;
    }

    /**
     * 获取第一条记录位置，用于数据库查询时设置的第一条记录
     *
     * @return
     */
    public int getFirstResult() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }

}
