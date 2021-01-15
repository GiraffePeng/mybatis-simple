package com.peng.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.alibaba.fastjson.JSON;
import com.peng.dto.MemberDetail;

//MappedTypes指定 要转化的类包括哪些 最终会以 key(类) value(对应的TypeHandler)的形式 存储在TypeHandlerRegistry中
@MappedTypes(value = {MemberDetail.class})
@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

	private Class<T> clazz;

	//构造方法传入要 JSON对应的类与String之间转换的 类类型
    public JsonTypeHandler(Class<T> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = clazz;
    }
    
    //将JSON类 转为 String 
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, JSON.toJSONString(parameter));
	}

	//将String 转为JSON类
	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return JSON.parseObject(rs.getString(columnName), clazz);
	}

	//将String 转为JSON类
	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return JSON.parseObject(rs.getString(columnIndex), clazz);
	}

	//将String 转为JSON类
	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return JSON.parseObject(cs.getString(columnIndex), clazz);
	}
}
