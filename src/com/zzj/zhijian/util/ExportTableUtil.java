package com.zzj.zhijian.util;

public class ExportTableUtil {
	public static final int USER_COLUMNS_LENGTH = 12;
	public static final String USER_COLUMN_ID = "ID";
	public static final String USER_COLUMN_TRUENAME = "真实姓名";
	public static final String USER_COLUMN_USERNAME = "用户名";
	public static final String USER_COLUMN_PASSWORD = "密码";
	public static final String USER_COLUMN_SEX = "性别";
	public static final String USER_COLUMN_BIRTHDAY = "出生日期";
	public static final String USER_COLUMN_DENTITYCODE = "身份证号";
	public static final String USER_COLUMN_EMAIL = "邮箱";
	public static final String USER_COLUMN_MOBILE = "联系电话";
	public static final String USER_COLUMN_ADDRESS = "收货地址";
	public static final String USER_COLUMN_STATUS = "用户状态";
	public static final String USER_COLUMN_CREATETIME = "创建时间";
	public static final String[] USER_COLUMNS = { USER_COLUMN_ID,
			USER_COLUMN_TRUENAME, USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD,
			USER_COLUMN_SEX, USER_COLUMN_BIRTHDAY, USER_COLUMN_DENTITYCODE,
			USER_COLUMN_EMAIL, USER_COLUMN_MOBILE, USER_COLUMN_ADDRESS,
			USER_COLUMN_STATUS, USER_COLUMN_CREATETIME };
	public static final int ORDER_COLUMNS_LENGTH = 7;
	public static final String ORDER_COLUMN_ID = "编号";
	public static final String ORDER_COLUMN_ORDERNO = "订单号";
	public static final String ORDER_COLUMN_USER_ID = "会员ID";
	public static final String ORDER_COLUMN_USER_NAME = "会员名称";
	public static final String ORDER_COLUMN_CREATETIME = "下单时间";
	public static final String ORDER_COLUMN_COST = "总金额(元)";
	public static final String ORDER_COLUMN_STATUS = "订单状态";
	public static final String[] ORDER_COLUMNS = { ORDER_COLUMN_ID,
			ORDER_COLUMN_ORDERNO, ORDER_COLUMN_USER_ID, ORDER_COLUMN_USER_NAME,
			ORDER_COLUMN_CREATETIME, ORDER_COLUMN_COST, ORDER_COLUMN_STATUS };
}
