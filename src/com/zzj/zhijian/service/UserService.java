package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.entity.DataCount;
import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.entity.User;

/**
 * �û�Serivce�ӿ�
 * 
 * @author zhaozengjie
 *
 */
public interface UserService
{

	/**
	 * �����û�
	 * 
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * �Ƿ����ָ���û������û�
	 * 
	 * @param userName
	 * @return
	 */
	public boolean existUserWithUserName(String userName);

	/**
	 * �û���¼
	 * 
	 * @param user
	 */
	public User login(User user);

	/**
	 * ��ҳ��ѯ�û�
	 * 
	 * @param s_user
	 * @param pageBean
	 * @return
	 */
	public List<User> findUserList(User s_user, PageBean pageBean);
	/**
	 * ��ѯ�����û�
	 * @param s_user
	 * @return
	 */
	public List<User> findUserList(User s_user);
	/**
	 * ��ѯ�û�����
	 * 
	 * @param s_user
	 * @return
	 */
	public Long getUserCount(User s_user);

	/**
	 * ɾ���û�
	 * 
	 * @param user
	 */
	public void delete(User user);

	/**
	 * ͨ��id��ȡ�û�ʵ��
	 * 
	 * @return
	 */
	public User getUserById(int id);
	
	/**
	 * ע���Աͳ�ƣ�ͳ�Ʊ���
	 * @param n
	 * @return
	 */
	public List<DataCount> getUserCount(int n);
}
