package com.zzj.zhijian.action;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import com.zzj.zhijian.entity.Order;
import com.zzj.zhijian.entity.OrderItem;
import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.entity.Product;
import com.zzj.zhijian.entity.ShoppingCart;
import com.zzj.zhijian.entity.ShoppingCartItem;
import com.zzj.zhijian.entity.User;
import com.zzj.zhijian.service.OrderService;
import com.zzj.zhijian.service.ProductService;
import com.zzj.zhijian.util.ByteUtil;
import com.zzj.zhijian.util.DateUtil;
import com.zzj.zhijian.util.ExportTableUtil;
import com.zzj.zhijian.util.NavUtil;
import com.zzj.zhijian.util.ResponseUtil;
import com.zzj.zhijian.util.StringUtil;

/**
 * 订单Action类
 * 
 * @author zhaozengjie
 *
 */
@SuppressWarnings("deprecation")
@Controller
public class OrderAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	@Resource
	private OrderService orderService;
	@Resource
	private ProductService productService;
	private String mainPage;
	private String navCode;

	private Order s_order;
	private List<Order> orderList;
	private int status;
	private String orderNo;

	private String page;
	private String rows;

	private String id;

	private String orderNos;
	private int stockstatus;
	
	// 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	private InputStream excelStream;
	private String fileName; // 文件名

	public int getStockstatus()
	{
		return stockstatus;
	}

	public void setStockstatus(int stockstatus)
	{
		this.stockstatus = stockstatus;
	}

	public String getOrderNos()
	{
		return orderNos;
	}

	public void setOrderNos(String orderNos)
	{
		this.orderNos = orderNos;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public Order getS_order()
	{
		return s_order;
	}

	public void setS_order(Order s_order)
	{
		this.s_order = s_order;
	}

	public List<Order> getOrderList()
	{
		return orderList;
	}

	public void setOrderList(List<Order> orderList)
	{
		this.orderList = orderList;
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

	public InputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * 保存订单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		HttpSession session = request.getSession();
		Order order = new Order();
		User currentUsre = (User) session.getAttribute("currentUser");
		order.setUser(currentUsre);
		order.setCreateTime(new Date());
		order.setOrderNo(DateUtil.getCurrentDateStr());
		order.setStatus(1);

		ShoppingCart shoppingCart = (ShoppingCart) session
				.getAttribute("shoppingCart");
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart
				.getShoppingCartItems();

		float cost = 0;
		List<OrderItem> orderItemList = new LinkedList<OrderItem>();

		for (ShoppingCartItem shoppingCartItem : shoppingCartItemList)
		{
			Product p = productService.getProductById(shoppingCartItem
					.getProduct().getId());
			if (shoppingCartItem.getCount() > p.getStock())
			{
				stockstatus = 0;
				break;
			} else
			{
				stockstatus = 1;
			}
			Product product = shoppingCartItem.getProduct();
			OrderItem orderItem = new OrderItem();
			orderItem.setNum(shoppingCartItem.getCount());
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			cost += product.getPrice() * shoppingCartItem.getCount();
			orderItemList.add(orderItem);
		}
		if (stockstatus == 1)
		{
			order.setOrderItemList(orderItemList);
			order.setCost(cost);
			orderService.saveOrder(order);
			navCode = NavUtil.genNavCode("购物");
			mainPage = "shopping/shopping-result.jsp";
			session.removeAttribute("shoppingCart");
		} else
		{
			navCode = NavUtil.genNavCode("购物");
			mainPage = "shopping/shopping-result.jsp";
		}
		return SUCCESS;
	}

	/**
	 * 查询订单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findOrder() throws Exception
	{
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if (s_order == null)
		{
			s_order = new Order();
		}
		s_order.setUser(currentUser);
		orderList = orderService.findOrder(s_order, null);
		navCode = NavUtil.genNavCode("个人中心");
		mainPage = "userCenter/orderList.jsp";
		return "orderList";
	}

	/**
	 * 确认收货
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confirmReceive() throws Exception
	{
		orderService.updateOrderStatus(status, orderNo);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 查询订单集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<Order> orderList = orderService.findOrder(s_order, pageBean);
		long total = orderService.getOrderCount(s_order);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "orderItemList" });
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		jsonConfig.registerJsonValueProcessor(User.class,
				new ObjectJsonValueProcessor(new String[] { "id", "userName" },
						User.class));
		JSONArray rows = JSONArray.fromObject(orderList, jsonConfig);
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 通过订单号查询商品集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findProductListByOrderId() throws Exception
	{
		if (StringUtil.isEmpty(id))
		{
			return null;
		}
		Order order = orderService.getOrderById(Integer.parseInt(id));
		List<OrderItem> orderItemList = order.getOrderItemList();
		JSONObject result = new JSONObject();
		JSONArray rows = new JSONArray();
		for (OrderItem orderItem : orderItemList)
		{
			Product product = orderItem.getProduct();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("productName", product.getName());
			jsonObject.put("proPic", product.getProPic());
			jsonObject.put("price", product.getPrice());
			jsonObject.put("num", orderItem.getNum());
			jsonObject.put("subtotal", product.getPrice() * orderItem.getNum());
			rows.add(jsonObject);
		}
		result.put("rows", rows);
		result.put("total", rows.size());
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String printView()throws Exception{
		if (StringUtil.isEmpty(id))
		{
			return null;
		}
		Order order = orderService.getOrderById(Integer.parseInt(id));
		HttpSession session = request.getSession();
		session.setAttribute("p_order", order);
		return "printview";
	}

	/**
	 * 修改订单状态
	 * 
	 * @return
	 * @throws Exception
	 */
	public String modifyOrderStatus() throws Exception
	{
		String[] orderNoStr = orderNos.split(",");
		for (int i = 0; i < orderNoStr.length; i++)
		{
			orderService.updateOrderStatus(status, orderNoStr[i]);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String doExportOrder() throws Exception
	{
		List<Order> orderList = orderService.findOrder(s_order);
		if (orderList == null)
		{
			JSONObject result = new JSONObject();
			result.put("success", false);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
			return null;
		} else
		{
			HSSFWorkbook workbook = this.getWorkbook(orderList);
			if (workbook != null)
			{
				Calendar c = Calendar.getInstance();
				long d = c.getTimeInMillis();
				this.fileName = "order_R" + d;
				excelStream = ByteUtil.workbook2InputStream(workbook);
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

	private HSSFWorkbook getWorkbook(List<Order> list)
	{
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作表
		HSSFCellStyle style = workbook.createCellStyle();
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
		cell.setCellValue("订单数据");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		row = sheet.createRow(1);
		for (int i = 0; i < ExportTableUtil.ORDER_COLUMNS_LENGTH; i++)
		{
			sheet.setColumnWidth(i, 22*256);
			cell = row.createCell(i);
			cell.setCellValue(ExportTableUtil.ORDER_COLUMNS[i]);
			cell.setCellStyle(style);
		}
		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow(i + 2);
			cell = row.createCell(0);
			cell.setCellValue(list.get(i).getId());
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellValue(list.get(i).getOrderNo());
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue(list.get(i).getUser().getId());
			cell.setCellStyle(style);
			cell = row.createCell(3);
			cell.setCellValue(list.get(i).getUser().getUserName());
			cell.setCellStyle(style);
			cell = row.createCell(4);
			cell.setCellValue(list.get(i).getCreateTime().toString());
			cell.setCellStyle(style);
			cell = row.createCell(5);
			cell.setCellValue(list.get(i).getCost());
			cell.setCellStyle(style);
			cell = row.createCell(6);
			switch (list.get(i).getStatus())
			{
			case 1:
				cell.setCellValue("待审核" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			case 2:
				cell.setCellValue("审核通过" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			case 3:
				cell.setCellValue("卖家已发货" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			case 4:
				cell.setCellValue("交易已完成" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			case 5:
				cell.setCellValue("退单待审核" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			case 6:
				cell.setCellValue("退单审核通过" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			case 7:
				cell.setCellValue("已退单" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			case 8:
				cell.setCellValue("已取消" + "(code:" + list.get(i).getStatus()
						+ ")");
				break;
			default:
				break;
			}
			cell.setCellStyle(style);

		}

		return workbook;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

}
