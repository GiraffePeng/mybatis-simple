package com.peng.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.peng.entity.Member;
import com.peng.mybatis.plugins.Page;

public interface MemberMapper {

	public Member selectByPrimaryKey(Long id);
	
	public int insertSelective(Member member);

	public List<Member> selectListByPage(Page page, Map<String, Object> paramMap);
}
