package com.gyf.bookstore.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.gyf.bookstore.model.User;
import com.gyf.bookstore.utils.C3P0Utils;

public class UserDao {

	/**
	 * @param user
	 * @throws SQLException 
	 */
	public void addUser(User user) throws SQLException{
		//1.QueryRunner
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		//2.sql
		String sql = "insert into user";
		sql += " (username,PASSWORD,gender,email,telephone,introduce,activeCode,state,role,registTime)";
		sql += " values(?,?,?,?,?,?,?,?,?,?)";
		
		//3参数
		/*Object[] prams = new Object[10];
		prams[0]*/
		List<Object> list = new ArrayList<Object>();
		list.add(user.getUsername());
		list.add(user.getPassword());
		list.add(user.getGender());
		list.add(user.getEmail());
		list.add(user.getTelephone());
		list.add(user.getIntroduce());
		list.add(user.getActiveCode());
		list.add(user.getState());
		list.add(user.getRole());
		list.add(user.getRegistTime());
		
		//4.执行sql
		qr.update(sql, list.toArray());
	}
	
	//通过激活码找到用户
	public User findUserByActiveCode(String activeCode) throws SQLException{
		//1.QueryRunner
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		//2.sql
		String sql = "select * from user where activeCode=?";
		
		return qr.query(sql, new BeanHandler<User>(User.class),activeCode);
	}
	
	//通过激活码更新用户的状态
	public void updateState(String activeCode) throws SQLException{
		//1.QueryRunner
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
		//2.sql
		String sql = "update user set state = 1 where activeCode=?";
		
		qr.update(sql, activeCode);
	}
	
	public User findUserByUsernameAndPassword(String username,String password) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where username = ? and password = ?";
		return qr.query(sql, new BeanHandler<User>(User.class),username,password);
	}
}
