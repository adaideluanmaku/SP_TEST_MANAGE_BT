package com.ch.sysuntils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class Select2 implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 总数
	 */
	private Long total_count = 0L;
	
	/**
	 * 数据
	 */
	private List<Object> results = new ArrayList<Object>();
	

	public Long getTotal() {
		return total_count;
	}

	public void setTotal(Long total) {
		this.total_count = total;
	}

	public List<Object> getResults() {
		return results;
	}

	public void setResults(List<Object> rows) {
		this.results = rows;
	}

}
