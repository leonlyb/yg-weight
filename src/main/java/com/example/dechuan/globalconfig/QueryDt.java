package com.example.dechuan.globalconfig;


import java.io.Serializable;


public class QueryDt implements Serializable{
    /**
     * 页数
     */
	private Integer pageNum=1;
    /**
     * 条数
     */

	private Integer pageSize=15;
	
	
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	
	
	
	

}
