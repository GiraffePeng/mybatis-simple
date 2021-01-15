package com.peng.mybatis.plugins;

import java.io.Serializable;
import java.util.Map;

public class Page implements Serializable {

	//当前页
	private Integer current;
	
	//一页展示几条数据
	private Integer size;
	

	public Page(Integer current, Integer size) {
		super();
		this.current = current;
		this.size = size;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
}
