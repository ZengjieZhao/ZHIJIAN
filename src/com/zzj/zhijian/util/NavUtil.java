package com.zzj.zhijian.util;

/**
 * 导航工具类
 * 
 * @author zhaozengjie
 *
 */
public class NavUtil {

	/**
	 * 生成导航代码
	 * 
	 * @param subName
	 * @return
	 */
	public static String genNavCode(String subName)
	{
		StringBuffer navCode = new StringBuffer();
		navCode.append(subName);
		return navCode.toString();
	}
}
