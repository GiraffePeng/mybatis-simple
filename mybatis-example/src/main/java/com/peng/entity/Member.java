package com.peng.entity;

import java.io.Serializable;
import java.util.List;

import com.peng.dto.MemberDetail;

public class Member implements Serializable {

	private Integer id;
	
	private String name;
	
	private String phone;
	
	//一对多关系
	private List<PurchaseLog> purchaseLogs;
	
	private MemberDetail detail;
	
	public Member(Integer id, String name, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
	
	public Member() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<PurchaseLog> getPurchaseLogs() {
		return purchaseLogs;
	}

	public void setPurchaseLogs(List<PurchaseLog> purchaseLogs) {
		this.purchaseLogs = purchaseLogs;
	}

	public MemberDetail getDetail() {
		return detail;
	}

	public void setDetail(MemberDetail detail) {
		this.detail = detail;
	}


}
