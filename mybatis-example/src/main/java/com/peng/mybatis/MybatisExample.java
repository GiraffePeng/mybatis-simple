package com.peng.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.peng.dto.MemberDetail;
import com.peng.entity.Member;
import com.peng.mybatis.mapper.MemberMapper;
import com.peng.mybatis.plugins.Page;

public class MybatisExample {

	public static void main(String[] args) throws IOException {
		String resource = "mybatis-config.xml"; 
		//定位到mybatis全局配置文件
		InputStream inputStream = Resources.getResourceAsStream(resource);
		//通过配置文件，构建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//开启sqlSession会话
		SqlSession session = sqlSessionFactory.openSession();
		
		//这里是 sqlSession的第一种使用方式，直接传入statementId以及参数
		//Member member = (Member) session.selectOne("selectByPrimaryKey", 1L);
		
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		Page page = new Page(0, 10);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", "张三");
		List<Member> members = mapper.selectListByPage(page, paramMap);
		System.out.println(members.size());
		//Member member = mapper.selectByPrimaryKey(1L);
		//System.out.println("---分割线,用于验证延迟加载的逻辑-----");
		//System.out.println("---调用getName()方法，不执行延迟加载-----");
		//System.out.println(member.getName());
		
		//System.out.println("---调用equals()方法，执行延迟加载-----");
		//member.equals(new Member());
		
		//MemberDetail memberDetail = member.getDetail();
		//关闭sqlsession
		session.close();
	}
}
