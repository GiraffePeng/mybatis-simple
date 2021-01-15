package com.peng.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.peng.entity.Member;

public class JdbcDB {

	public static void main(String[] args) {
		try {
			//注册JDBC驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			String password = "root"; 
			String user = "root";
			String url = "jdbc:mysql://localhost:3306/mossad?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true";
			
			//打开链接
			Connection connection = DriverManager.getConnection(url, user, password);
			
			//执行查询
			Statement statement = connection.createStatement();
			String sql = "select id, name, phone from member";
			ResultSet rs = statement.executeQuery(sql);
			
			//获取结果集
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				//转为entity
				Member member = new Member(id, name, phone);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
