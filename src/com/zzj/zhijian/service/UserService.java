package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.bean.DataCount;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.bean.User;

/**
 * 用户Serivce接口
 * 
 * @author zhaozengjie
 *
 */
public interface UserService
{

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * 是否存在指定用户名的用户
	 * 
	 * @param userName
	 * @return
	 */
	public boolean existUserWithUserName(String userName);

	/**
	 * 用户登录
	 * 
	 * @param user
	 */
	public User login(User user);

	/**
	 * 分页查询用户
	 * 
	 * @param s_user
	 * @param pageBean
	 * @return
	 */
	public List<User> findUserList(User s_user, PageBean pageBean);
	/**
	 * 查询所有用户
	 * @param s_user
	 * @return
	 */
	public List<User> findUserList(User s_user);
	/**
	 * 查询用户数量
	 * 
	 * @param s_user
	 * @return
	 */
	public Long getUserCount(User s_user);

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	public void delete(User user);

	/**
	 * 通过id获取用户实体
	 * 
	 * @return
	 */
	public User getUserById(int id);
	
	/**
	 * 注册会员统计（统计报表）
	 * @param n
	 * @return
	 */
	public List<DataCount> getUserCount(int n);
}
