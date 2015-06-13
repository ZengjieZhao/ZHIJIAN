package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.bean.Admin;
import com.zzj.zhijian.bean.PageBean;

public interface AdminService
{
	Admin login(Admin admin);

	List<Admin> findAdminList(Admin admin, PageBean pageBean);

	Long getAdminCount(Admin s_admin);

	void saveAdmin(Admin admin);

	void delete(Admin admin);

	Admin getAdminById(int id);

	boolean existAdminWithAdminName(String name);
}
