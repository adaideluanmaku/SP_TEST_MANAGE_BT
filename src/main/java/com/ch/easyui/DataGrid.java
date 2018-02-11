package com.ch.easyui;

import java.util.ArrayList;
import java.util.List;

public class DataGrid implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 总数
	 */
	private Long total = 0L;
	
	/**
	 * 数据
	 */
	private List<Object> rows = new ArrayList<Object>();
	
	/**
	 * foot可不处理
	 */
	private List<Object> footer = new ArrayList<Object>();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public List<Object> getFooter() {
		return footer;
	}

	public void setFooter(List<Object> footer) {
		this.footer = footer;
	}
	

}
