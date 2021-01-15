package com.peng.dto;

import java.io.Serializable;

public class MemberDetail implements Serializable {

	private Integer sex;
	
	private Integer age;

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
