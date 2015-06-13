package com.zzj.zhijian.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzj.zhijian.bean.Admin;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.dao.BaseDAO;
import com.zzj.zhijian.service.AdminService;
import com.zzj.zhijian.util.StringUtil;

@Service("adminService")
public class AdminServiceImpl implements AdminService
{

	@Resource
	private BaseDAO<Admin> baseDAO;

	@Override
	public Admin login(Admin admin)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer(
				"from Admin a where a.name=? and a.password=?");
		param.add(admin.getName());
		param.add(admin.getPassword());
		return baseDAO.get(hql.toString(), param);
	}

	@Override
	public List<Admin> findAdminList(Admin admin, PageBean pageBean)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Admin");
		if (admin != null)
		{
			if (StringUtil.isNotEmpty(admin.getName()))
			{
				hql.append(" and name like ? ");
				param.add("%" + admin.getName() + "%");
			}
		}
		hql.append(" and auth=1");
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
	public Long getAdminCount(Admin s_admin)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Admin");
		if (s_admin != null)
		{
			if (StringUtil.isNotEmpty(s_admin.getName()))
			{
				hql.append(" and name like ? ");
				param.add("%" + s_admin.getName() + "%");
			}
		}
		hql.append(" and auth=1");
		return baseDAO
				.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void saveAdmin(Admin admin)
	{
		baseDAO.merge(admin);
	}

	@Override
	public void delete(Admin admin)
	{
		baseDAO.delete(admin);
	}

	@Override
	public Admin getAdminById(int id)
	{
		return baseDAO.get(Admin.class, id);
	}

	@Override
	public boolean existAdminWithAdminName(String name)
	{
		String hql = "select count(*) from Admin where name=?";
		long count = baseDAO.count(hql, new Object[] { name });
		if (count > 0)
		{
			return true;
		} else
		{
			return false;
		}
	}
}
