package com.zzj.zhijian.util;

/**
 * ����������
 * 
 * @author zhaozengjie
 *
 */
public class NavUtil {

	/**
	 * ���ɵ�������
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
