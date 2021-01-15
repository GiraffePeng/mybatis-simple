package com.peng.db;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.druid.pool.DruidDataSource;
import com.peng.entity.Member;

public class SpringJDBC {

	private static String JDBC_URL = "jdbc:mysql://rm-2ze32c7y6a8h49ac84o.mysql.rds.aliyuncs.com:3306/mossad?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true";

	private static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	private static String USERNAME = "charles";

	private static String PASSWORD = "asdqwe123@";

	private static DruidDataSource druidDataSource;

	private static JdbcTemplate jdbcTemplate;

	public static void init() {
		druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(JDBC_URL);
		druidDataSource.setDriverClassName(DRIVER_CLASS_NAME);
		druidDataSource.setUsername(USERNAME);
		druidDataSource.setPassword(PASSWORD);

		jdbcTemplate = new JdbcTemplate(druidDataSource);
	}

	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public static void main(String[] args) {
		SpringJDBC.init();
		JdbcTemplate template = SpringJDBC.getJdbcTemplate();
		String sql = "select id, name, phone from member";
		List<Member> result = template.query(sql, new BaseRowMapper<Member>(Member.class));
		System.out.println(result.get(0).getName());
	}
	
	public static class MemberRowMapper implements RowMapper<Member> {

		@Override
		public Member mapRow(ResultSet rs, int i) throws SQLException {
			return new Member(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
		}
		
	}
	
	public static class BaseRowMapper<T> implements RowMapper<T> {

		//目标类类型
		private Class<?> targetClass;

		//属性map 
		private Map<String, Field> fieldMap = new HashMap<String, Field>();
		
		public BaseRowMapper(Class<?> clazz) {
			targetClass = clazz;
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				fieldMap.put(field.getName(), field);
			}
		} 
		
		@Override
		public T mapRow(ResultSet rs, int i) throws SQLException {
			T result = null;
			try {
				//通过无参构造函数实例化对象
				result = (T) targetClass.newInstance();
				//获取表元数据
				ResultSetMetaData metaData = rs.getMetaData();
				//获取表一共多少列
				int columnCount = metaData.getColumnCount();
				for (int j = 1; j <= columnCount; j++) {
					//获取列名
					String columnName = metaData.getColumnName(j);
					//下划线转驼峰  通过列名找到对应的属性名
					Field field = fieldMap.get(camel(columnName));
					if(field != null) {
						field.setAccessible(true);
						//利用反射赋值
						Class<?> type = field.getType();
						if(type == String.class) {
							field.set(result, rs.getString(columnName));
						}else if(type == Integer.class) {
							field.set(result, rs.getInt(columnName));
						}
						field.setAccessible(false);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		/**
	     * 下划线转驼峰
	     * @param str
	     * @return
	     */
	    public static String camel(String str) {
	        Pattern pattern = Pattern.compile("_(\\w)");
	        Matcher matcher = pattern.matcher(str);
	        StringBuffer sb = new StringBuffer(str);
	        if(matcher.find()) {
	            sb = new StringBuffer();
	            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
	            matcher.appendTail(sb);
	        }else {
	            return sb.toString();
	        }
	        return camel(sb.toString());
	    }
		
	}
}
