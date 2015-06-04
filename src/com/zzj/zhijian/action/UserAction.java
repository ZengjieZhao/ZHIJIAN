package com.zzj.zhijian.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.entity.Admin;
import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.entity.User;
import com.zzj.zhijian.service.AdminService;
import com.zzj.zhijian.service.OrderService;
import com.zzj.zhijian.service.UserService;
import com.zzj.zhijian.util.ByteUtil;
import com.zzj.zhijian.util.ExportTableUtil;
import com.zzj.zhijian.util.NavUtil;
import com.zzj.zhijian.util.ResponseUtil;

/**
 * 用户Action类
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class UserAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserService userService;
	@Resource
	private AdminService adminService;
	@Resource
	private OrderService orderService;

	private HttpServletRequest request;

	private String userName;
	private User user;
	private String error;
	private Admin admin;
	private String imageCode;

	private String mainPage;
	private String navCode;

	private User s_user;
	private Admin s_admin;
	private String page;
	private String rows;

	private String ids;

	private Double totalSales; // 销售总额：
	private Double lastYearTotalSales; // 上年全年销售(至今)：
	private Double thisYearTotalSales; // 今年全年销售(至今)：
	private Long tNumberOfOrder; // 总订单数(已成交)：
	private Long tNumberOfMember; // 总会员数：
	private Long tNumberOfNoBuyMember; // 未购买过商品的会员数：

	// 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	private InputStream excelStream;
	private String fileName; // 文件名

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public User getS_user()
	{
		return s_user;
	}

	public void setS_user(User s_user)
	{
		this.s_user = s_user;
	}

	public Admin getS_admin()
	{
		return s_admin;
	}

	public void setS_admin(Admin s_admin)
	{
		this.s_admin = s_admin;
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getRows()
	{
		return rows;
	}

	public void setRows(String rows)
	{
		this.rows = rows;
	}

	public String getMainPage()
	{
		return mainPage;
	}

	public void setMainPage(String mainPage)
	{
		this.mainPage = mainPage;
	}

	public String getNavCode()
	{
		return navCode;
	}

	public void setNavCode(String navCode)
	{
		this.navCode = navCode;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public User getUser()
	{
		return user;
	}

	public Admin getAdmin()
	{
		return admin;
	}

	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getImageCode()
	{
		return imageCode;
	}

	public void setImageCode(String imageCode)
	{
		this.imageCode = imageCode;
	}

	public Double getTotalSales()
	{
		return totalSales;
	}

	public void setTotalSales(Double totalSales)
	{
		this.totalSales = totalSales;
	}

	public Double getLastYearTotalSales()
	{
		return lastYearTotalSales;
	}

	public void setLastYearTotalSales(Double lastYearTotalSales)
	{
		this.lastYearTotalSales = lastYearTotalSales;
	}

	public Double getThisYearTotalSales()
	{
		return thisYearTotalSales;
	}

	public void setThisYearTotalSales(Double thisYearTotalSales)
	{
		this.thisYearTotalSales = thisYearTotalSales;
	}

	public Long gettNumberOfOrder()
	{
		return tNumberOfOrder;
	}

	public void settNumberOfOrder(Long tNumberOfOrder)
	{
		this.tNumberOfOrder = tNumberOfOrder;
	}

	public Long gettNumberOfMember()
	{
		return tNumberOfMember;
	}

	public void settNumberOfMember(Long tNumberOfMember)
	{
		this.tNumberOfMember = tNumberOfMember;
	}

	public Long gettNumberOfNoBuyMember()
	{
		return tNumberOfNoBuyMember;
	}

	public void settNumberOfNoBuyMember(Long tNumberOfNoBuyMember)
	{
		this.tNumberOfNoBuyMember = tNumberOfNoBuyMember;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public InputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	/**
	 * 判断指定用户名的用户是否存在
	 * 
	 * @return
	 * @throws Exception
	 */
	public String existUserWithUserName() throws Exception
	{
		boolean exist = userService.existUserWithUserName(userName);
		JSONObject result = new JSONObject();
		if (exist)
		{
			result.put("exist", true);
		} else
		{
			result.put("exist", false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception
	{
		HttpSession session = request.getSession();
		/*
		 * if(user.getStatus()!=2) { user.setStatus(2); }
		 */
		User currentUser = userService.login(user);

		if (!imageCode.equals(session.getAttribute("sRand")))
		{
			error = "验证码错误！";
			return ERROR;
		} else if (currentUser == null)
		{
			error = "用户名或密码错误！";
			return ERROR;
		} else
		{
			session.setAttribute("currentUser", currentUser);
		}
		if (user.getStatus() == 2)
		{
			return "adminLogin";
		} else
		{
			return "login";
		}
	}

	/**
	 * 管理员登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginAdmin() throws Exception
	{
		HttpSession session = request.getSession();
		Admin currentAdmin = adminService.login(admin);
		if (!imageCode.equals(session.getAttribute("sRand")))
		{
			error = "验证码错误！";
			return "adminError";

		} else if (currentAdmin == null)
		{
			error = "用户名或密码错误！";
			return "adminError";
		} else
		{
			session.setAttribute("currentAdmin", currentAdmin);
		}
		return "adminLogin";
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	public String register() throws Exception
	{
		user.setCreateTime(new Date());
		userService.saveUser(user);
		return "register_success";
	}

	/**
	 * 注销用户
	 * 
	 * @throws Exception
	 */
	public String logout() throws Exception
	{
		request.getSession().invalidate();
		return "logout";
	}

	/**
	 * 注销管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout2() throws Exception
	{
		request.getSession().invalidate();
		return "logout2";
	}

	/**
	 * 用户中心
	 * 
	 * @return
	 * @throws Exception
	 */
	public String userCenter() throws Exception
	{
		navCode = NavUtil.genNavCode("个人中心");
		mainPage = "userCenter/ucDefault.jsp";
		return "userCenter";
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserInfo() throws Exception
	{
		navCode = NavUtil.genNavCode("个人中心");
		mainPage = "userCenter/userInfo.jsp";
		return "userCenter";
	}

	/**
	 * 修改个人信息预操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String preSave() throws Exception
	{
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("currentUser");
		navCode = NavUtil.genNavCode("个人中心");
		mainPage = "userCenter/userSave.jsp";
		return "userCenter";
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		HttpSession session = request.getSession();
		userService.saveUser(user);
		session.setAttribute("currentUser", user);
		navCode = NavUtil.genNavCode("个人中心");
		mainPage = "userCenter/userInfo.jsp";
		return "userCenter";
	}

	/**
	 * 分页查询用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<User> userList = userService.findUserList(s_user, pageBean);
		long total = userService.getUserCount(s_user);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "orderList" });
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(userList, jsonConfig);
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 分页查询管理员信息
	 */
	public String adminList() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<Admin> adminList = adminService.findAdminList(s_admin, pageBean);
		long total = adminService.getAdminCount(s_admin);
		JSONArray rows = JSONArray.fromObject(adminList);
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteUser() throws Exception
	{
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			User u = userService.getUserById(Integer.parseInt(idsStr[i]));
			userService.delete(u);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 删除管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteAdmin() throws Exception
	{
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			Admin a = adminService.getAdminById(Integer.parseInt(idsStr[i]));
			adminService.delete(a);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 保存添加的用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveUser() throws Exception
	{
		JSONObject result = new JSONObject();
		if (user.getId() != 0)
		{
			userService.saveUser(user);
			result.put("success", true);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
			return null;
		}
		if (userService.existUserWithUserName(user.getUserName()))
		{
			result.put("success", false);
		} else
		{
			user.setCreateTime(new Date());
			userService.saveUser(user);
			result.put("success", true);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 保存添加的管理员信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveAdmin() throws Exception
	{
		JSONObject result = new JSONObject();
		if (admin.getId() != 0)
		{
			adminService.saveAdmin(admin);
			result.put("success", true);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
			return null;
		}
		if (adminService.existAdminWithAdminName(admin.getName()))
		{
			result.put("success", false);
		} else
		{
			adminService.saveAdmin(admin);
			result.put("success", true);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String modifyPassword() throws Exception
	{
		User u = userService.getUserById(user.getId());
		u.setPassword(user.getPassword());
		userService.saveUser(u);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	/**
	 * 导出用户列表
	 * @return
	 * @throws Exception
	 */
	public String doExportUser() throws Exception
	{
		List<User> userList = userService.findUserList(s_user);
		if (userList == null)
		{
			JSONObject result = new JSONObject();
			result.put("success", false);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
			return null;
		} else
		{
			HSSFWorkbook workbook = this.getWorkbook(userList);
			if (workbook != null)
			{
				Calendar c = Calendar.getInstance();
				long d = c.getTimeInMillis();
				/*int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				String month_ = new String("" + month);
				if (month < 10)
				{
					month_ = "0" + month;
				}
				int day = c.get(Calendar.DAY_OF_MONTH);
				String day_ = new String("" + day);
				if (day < 10)
				{
					day_ = "0" + day;
				}*/
				this.fileName = "user_R"+d;
				excelStream=ByteUtil.workbook2InputStream(workbook);
				return "export_success";
			} else
			{
				JSONObject result = new JSONObject();
				result.put("success", false);
				ResponseUtil.write(ServletActionContext.getResponse(), result);
				return null;
			}
		}
	}
	/**
	 * 创建一个excel文件。
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private HSSFWorkbook getWorkbook(List<User> list) throws Exception
	{
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作表
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建表单
		
		HSSFRow row = sheet.createRow(0); // 创建第一行 title
		HSSFCell cell = null;
		cell = row.createCell(0);
		cell.setCellValue("注册用户数据");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

		row = sheet.createRow(1);
		 for (int i = 0; i < ExportTableUtil.USER_COLUMNS_LENGTH; i++) { cell =
		  row.createCell(i); cell.setCellValue(ExportTableUtil.USER_COLUMNS[i]);
		cell.setCellStyle(style); }
		 
		// creatExportData

		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow(i + 2);//
			cell = row.createCell(0);
			cell.setCellValue(list.get(i).getId());
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellValue(list.get(i).getTrueName());
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue(list.get(i).getUserName());
			cell.setCellStyle(style);
			cell = row.createCell(3);
			cell.setCellValue(list.get(i).getPassword());
			cell.setCellStyle(style);
			cell = row.createCell(4);
			cell.setCellValue(list.get(i).getSex());
			cell.setCellStyle(style);
			cell = row.createCell(5);
			cell.setCellValue(list.get(i).getBirthday().toString());
			cell.setCellStyle(style);
			cell = row.createCell(6);
			cell.setCellValue(list.get(i).getDentityCode());
			cell.setCellStyle(style);
			cell = row.createCell(7);
			cell.setCellValue(list.get(i).getEmail());
			cell.setCellStyle(style);
			cell = row.createCell(8);
			cell.setCellValue(list.get(i).getMobile());
			cell.setCellStyle(style);
			cell = row.createCell(9);
			cell.setCellValue(list.get(i).getAddress());
			cell.setCellStyle(style);
			cell = row.createCell(10);
			if(list.get(i).getStatus()==1){
			cell.setCellValue("普通注册会员"+"(会员状态码："+list.get(i).getStatus()+")");
			}
			cell.setCellStyle(style);
			cell = row.createCell(11);
			cell.setCellValue(list.get(i).getCreateTime());
			cell.setCellStyle(style);
		}
		return workbook;
	}
}
