package com.zzj.zhijian.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzj.zhijian.bean.DataCount;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.bean.User;
import com.zzj.zhijian.dao.BaseDAO;
import com.zzj.zhijian.service.UserService;
import com.zzj.zhijian.util.StringUtil;

/**
 * 用户Service类
 * 
 * @author zhaozengjie
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService
{

	@Resource
	private BaseDAO<User> baseDAO;

	@Override
	public void saveUser(User user)
	{
		baseDAO.merge(user);
	}

	@Override
	public boolean existUserWithUserName(String userName)
	{
		String hql = "select count(*) from User where userName=?";
		long count = baseDAO.count(hql, new Object[] { userName });
		if (count > 0)
		{
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public User login(User user)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer(
				"from User u where u.userName=? and u.password=?");
		param.add(user.getUserName());
		param.add(user.getPassword());
		if (user.getStatus() == 2)
		{
			hql.append(" and u.status=2");
		}
		return baseDAO.get(hql.toString(), param);
	}

	@Override
	public List<User> findUserList(User s_user, PageBean pageBean)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from User");
		if (s_user != null)
		{
			if (StringUtil.isNotEmpty(s_user.getUserName()))
			{
				hql.append(" and userName like ? ");
				param.add("%" + s_user.getUserName() + "%");
			}
		}
		hql.append(" and status=1");
		if (pageBean != null)
		{
			return baseDAO.find(hql.toString().replaceFirst("and", "where"),
					param, pageBean);
		} else
		{
			return null;
		}
	}
	@Override
	public List<User> findUserList(User s_user){
		String hql ="from User";
		return baseDAO.find(hql);
	}
	@Override
	public Long getUserCount(User s_user)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from User u");
		if (s_user != null)
		{
			if (StringUtil.isNotEmpty(s_user.getUserName()))
			{
				hql.append(" and userName like ? ");
				param.add("%" + s_user.getUserName() + "%");
			}
			if(s_user.getOrderList()!=null&&s_user.getOrderList().size()==0)
			{
				hql.append(" and size(u.orderList)<=0 ");
			}
		}
		hql.append(" and status=1");
		System.out.println("usercount:"+hql.toString());
		return baseDAO
				.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void delete(User user)
	{
		baseDAO.delete(user);
	}

	@Override
	public User getUserById(int id)
	{
		return baseDAO.get(User.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DataCount> getUserCount(int n)
	{
		String hql = "";
		List<DataCount> dataCount = null;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		if (n == 1)
		{
			// 当天每小时
			hql = "select new com.zzj.zhijian.bean.DataCount(hour(createTime) as time, count(1) as count)  from User where year(createTime)="
					+ year
					+ " and month(createTime)="
					+ (month + 1)
					+ " and day(createTime)="
					+ day
					+ " group by hour(createTime)";
			dataCount = new ArrayList<DataCount>();
			System.out.println(">>>>>>>size:" + dataCount.size());
			for (int i = 0; i < 24; i++)
			{
				DataCount oc = new DataCount();
				oc.setTime(i);
				dataCount.add(oc);
			}
			List<DataCount> temp = new ArrayList<DataCount>();
			temp = (List<DataCount>) baseDAO.executeCommonhqlQeury(hql);
			System.out.println("tempsize：" + temp.size());
			for (int i = 0; i < 24; i++)
			{
				for (DataCount oc : temp)
				{
					if (oc.getTime() == i)
					{
						dataCount.get(i).setCount(oc.getCount());
					}
				}
			}
			for (int i = 0; i < 24; i++)
			{
				if (dataCount.get(i).getCount() == null)
				{
					dataCount.get(i).setCount(0L);
				}
			}
		} else if (n == 2)
		{
			// 每星期
			hql = "select new com.zzj.zhijian.bean.DataCount(hour(createTime) as time, count(1) as count)  from User where year(createTime)="
					+ year + " group by hour(createTime)";

		} else if (n == 3)
		{
			// 当月每天
			hql = "select new com.zzj.zhijian.bean.DataCount(day(createTime) as time, count(1) as count)  from User where year(createTime)="
					+ year
					+ " and month(createTime)="
					+ (month + 1)
					+ " group by day(createTime)";
			dataCount = new ArrayList<DataCount>(30);
			for (int i = 1; i < 31; i++)
			{
				DataCount oc = new DataCount();
				oc.setTime(i);
				dataCount.add(oc);
			}

			List<DataCount> temp = new ArrayList<DataCount>();
			temp = (List<DataCount>) baseDAO.executeCommonhqlQeury(hql);
			for (int i = 0; i < 30; i++)
			{
				for (DataCount oc : temp)
				{
					if (oc.getTime() == i + 1)
					{
						dataCount.get(i).setCount(oc.getCount());
					}
				}
			}
			for (int i = 0; i < 30; i++)
			{
				if (dataCount.get(i).getCount() == null)
				{
					dataCount.get(i).setCount(0L);
				}
			}
		} else if (n == 4)
		{
			// 当年每月
			hql = "select new com.zzj.zhijian.bean.DataCount(month(createTime) as time, count(1) as count)  from User where year(createTime)="
					+ year + " group by month(createTime)";
			dataCount = new ArrayList<DataCount>(12);
			for (int i = 1; i < 13; i++)
			{
				DataCount oc = new DataCount();
				oc.setTime(i);
				dataCount.add(oc);
			}

			List<DataCount> temp = new ArrayList<DataCount>();
			temp = (List<DataCount>) baseDAO.executeCommonhqlQeury(hql);
			for (int i = 0; i < 12; i++)
			{
				for (DataCount oc : temp)
				{
					if (oc.getTime() == i + 1)
					{
						dataCount.get(i).setCount(oc.getCount());
					}
				}
			}
			for (int i = 0; i < 12; i++)
			{
				if (dataCount.get(i).getCount() == null)
				{
					dataCount.get(i).setCount(0L);
				}
			}
		}
		return dataCount;
		
	}
}
