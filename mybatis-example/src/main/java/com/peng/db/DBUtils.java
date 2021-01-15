package com.peng.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.alibaba.druid.pool.DruidDataSource;
import com.peng.entity.Member;

/**
 * DButil测试类
 */
public class DBUtils {

	private static String JDBC_URL = "jdbc:mysql://localhost:3306/mossad?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true";

	private static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	private static String USERNAME = "localhost";

	private static String PASSWORD = "localhost";

	private static DruidDataSource druidDataSource;

	private static QueryRunner queryRunner;

	public static void init() {
		druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(JDBC_URL);
		druidDataSource.setDriverClassName(DRIVER_CLASS_NAME);
		druidDataSource.setUsername(USERNAME);
		druidDataSource.setPassword(PASSWORD);

		queryRunner = new QueryRunner(druidDataSource);
	}

	public static QueryRunner getQueryRunner() {
		return queryRunner;
	}

	public static void main(String[] args) {
		DBUtils.init();
		QueryRunner runner = DBUtils.getQueryRunner();
		String sql = "select id, name, phone from member";
		try {
			List<Member> members = runner.query(sql, new BeanListHandler<Member>(Member.class));
			System.out.println(members.get(0).getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
