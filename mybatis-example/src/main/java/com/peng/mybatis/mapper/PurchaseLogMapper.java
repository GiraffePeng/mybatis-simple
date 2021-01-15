package com.peng.mybatis.mapper;

import java.util.List;

import com.peng.entity.PurchaseLog;

public interface PurchaseLogMapper {

	public List<PurchaseLog> selectListByMemberId(Long memberId);
	
}
