package cn.tedu.note.service;

import java.io.Serializable;

import cn.tedu.note.entity.User;

public interface UserService 
	extends Serializable{
	
	/**
	 * 登录方法
	 * @param name 用户名
	 * @param password 密码
	 * @return 登录成功时候返回用户的信息
	 * @throws NameOrPasswrodException 
	 * 			用户名或密码错误
	 * 			用户名或密码为空
	 */
	User login(String name,String password) 
		throws NameOrPasswordException;
	
	/**
	 * 注册功能
	 * @param name
	 * @param password
	 * @param nick 昵称
	 * @return 注册成功以后的用户信息
	 * @throws UserExistException 用户名已经存在
	 */
	User regist(String name, 
			String password, String nick)
		throws UserExistException;
}









