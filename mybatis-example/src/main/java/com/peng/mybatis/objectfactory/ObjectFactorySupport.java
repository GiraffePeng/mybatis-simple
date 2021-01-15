package com.peng.mybatis.objectfactory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import com.peng.entity.Member;

public class ObjectFactorySupport extends DefaultObjectFactory {

	@Override
	public Object create(Class type) {
		//如果type类型为Member类
		if (type.equals(Member.class)) {
			System.out.println("命中member对象");
			Member member = (Member) super.create(type);
			System.out.println("创建好member对象,doSomething");
			return member;
		}
		//如果不是，则调用默认的构造方法进行创建
		Object result = super.create(type);
		return result;
	}
}
