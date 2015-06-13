package com.zzj.zhijian.action;

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
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.bean.Admin;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.bean.User;
import com.zzj.zhijian.service.AdminService;
import com.zzj.zhijian.service.OrderService;
import com.zzj.zhijian.service.UserService;
import com.zzj.zhijian.util.ByteUtil;
import com.zzj.zhijian.util.ExportTableUtil;

/**
 * �û�Action��
 * 
 * @author zhaozengjie
 *
 */
@SuppressWarnings("deprecation")
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

	private Double totalSales; // �����ܶ
	private Double lastYearTotalSales; // ����ȫ������(����)��
	private Double thisYearTotalSales; // ����ȫ������(����)��
	private Long tNumberOfOrder; // �ܶ�����(�ѳɽ�)��
	private Long tNumberOfMember; // �ܻ�Ա����
	private Long tNumberOfNoBuyMember; // δ�������Ʒ�Ļ�Ա����

	// �����������Ӧ����struts.xml�����õ��Ǹ�excelStream�����߱���һ��
	private InputStream excelStream;
	private String fileName; // �ļ���
	private JSONObject responseJson ;  
	public UserAction()
	{
		responseJson = new JSONObject();
	}

	public JSONObject getResponseJson()
	{
		return responseJson;
	}

	public void setResponseJson(JSONObject responseJson)
	{
		this.responseJson = responseJson;
	}

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
	 * �ж�ָ���û������û��Ƿ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String existUserWithUserName() throws Exception
	{
		boolean exist = userService.existUserWithUserName(userName);
		if (exist)
		{
			responseJson.put("exist", true);
		} else
		{
			responseJson.put("exist", false);
		}
		return "json";
	}

	/**
	 * �û���¼
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception
	{
		HttpSession session = request.getSession();
		User currentUser = userService.login(user);

		if (!imageCode.equals(session.getAttribute("sRand")))
		{
			error = "��֤�����";
			return ERROR;
		} else if (currentUser == null)
		{
			error = "�û������������";
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
	 * ����Ա��¼
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
			error = "��֤�����";
			return "adminError";

		} else if (currentAdmin == null)
		{
			error = "�û������������";
			return "adminError";
		} else
		{
			session.setAttribute("currentAdmin", currentAdmin);
		}
		return "adminLogin";
	}

	/**
	 * �û�ע��
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
	 * ע���û�
	 * 
	 * @throws Exception
	 */
	public String logout() throws Exception
	{
		request.getSession().invalidate();
		return "logout";
	}

	/**
	 * ע������Ա
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
	 * �û�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String userCenter() throws Exception
	{
		navCode ="��������";
		mainPage = "userCenter/ucDefault.jsp";
		return "userCenter";
	}

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserInfo() throws Exception
	{
		navCode = "��������";
		mainPage = "userCenter/userInfo.jsp";
		return "userCenter";
	}

	/**
	 * �޸ĸ�����ϢԤ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String preSave() throws Exception
	{
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("currentUser");
		navCode = "��������";
		mainPage = "userCenter/userSave.jsp";
		return "userCenter";
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		HttpSession session = request.getSession();
		userService.saveUser(user);
		session.setAttribute("currentUser", user);
		navCode = "��������";
		mainPage = "userCenter/userInfo.jsp";
		return "userCenter";
	}

	/**
	 * ��ҳ��ѯ�û���Ϣ
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
		responseJson.put("rows", rows);
		responseJson.put("total", total);
		return "json";
	}

	/**
	 * ��ҳ��ѯ����Ա��Ϣ
	 */
	public String adminList() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<Admin> adminList = adminService.findAdminList(s_admin, pageBean);
		long total = adminService.getAdminCount(s_admin);
		JSONArray rows = JSONArray.fromObject(adminList);
		responseJson.put("rows", rows);
		responseJson.put("total", total);
		return "json";
	}

	/**
	 * ɾ���û�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteUser() throws Exception
	{
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			User u = userService.getUserById(Integer.parseInt(idsStr[i]));
			userService.delete(u);
		}
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * ɾ������Ա
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteAdmin() throws Exception
	{
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			Admin a = adminService.getAdminById(Integer.parseInt(idsStr[i]));
			adminService.delete(a);
		}
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * ������ӵ��û���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveUser() throws Exception
	{
		if (user.getId() != 0)
		{
			userService.saveUser(user);
			responseJson.clear();
			responseJson.put("success", true);
			return "json";
		}
		if (userService.existUserWithUserName(user.getUserName()))
		{
			responseJson.clear();
			responseJson.put("success", false);
		} else
		{
			user.setCreateTime(new Date());
			userService.saveUser(user);
			responseJson.clear();
			responseJson.put("success", true);
		}
		return "json";
	}

	/**
	 * ������ӵĹ���Ա��Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveAdmin() throws Exception
	{
		if (admin.getId() != 0)
		{
			adminService.saveAdmin(admin);
			responseJson.clear();
			responseJson.put("success", true);
			return "saveAdmin";
		}
		if (adminService.existAdminWithAdminName(admin.getName()))
		{
			responseJson.clear();
			responseJson.put("success", false);
		} else
		{
			adminService.saveAdmin(admin);
			responseJson.clear();
			responseJson.put("success", true);
		}
		return "json";
	}

	/**
	 * �޸�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String modifyPassword() throws Exception
	{
		Admin a = adminService.getAdminById(admin.getId());
		a.setPassword(admin.getPassword());
		adminService.saveAdmin(a);
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	/**
	 * �����û��б�
	 * @return
	 * @throws Exception
	 */
	public String doExportUser() throws Exception
	{
		List<User> userList = userService.findUserList(s_user);
		if (userList == null)
		{
			responseJson.clear();
			responseJson.put("success", false);
			return "json";
		} else
		{
			HSSFWorkbook workbook = this.getWorkbook(userList);
			if (workbook != null)
			{
				Calendar c = Calendar.getInstance();
				long d = c.getTimeInMillis();
				this.fileName = "user_R"+d;
				excelStream=ByteUtil.workbook2InputStream(workbook);
				return "export_success";
			} else
			{
				responseJson.clear();
				responseJson.put("success", false);
				return "json";
			}
		}
	}
	/**
	 * ����һ��excel�ļ���
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getWorkbook(List<User> list) throws Exception
	{
		HSSFWorkbook workbook = new HSSFWorkbook(); // ����������
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		HSSFSheet sheet = workbook.createSheet("sheet1"); // ������
		
		HSSFRow row = sheet.createRow(0); // ������һ�� title
		HSSFCell cell = null;
		cell = row.createCell(0);
		cell.setCellValue("ע���û�����");
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
			cell.setCellValue("��ͨע���Ա"+"(��Ա״̬�룺"+list.get(i).getStatus()+")");
			}
			cell.setCellStyle(style);
			cell = row.createCell(11);
			cell.setCellValue(list.get(i).getCreateTime());
			cell.setCellStyle(style);
		}
		return workbook;
	}
}
