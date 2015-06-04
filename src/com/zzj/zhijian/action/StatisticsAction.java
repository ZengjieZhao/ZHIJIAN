/**
 * 
 */
package com.zzj.zhijian.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.entity.Order;
import com.zzj.zhijian.entity.DataCount;
import com.zzj.zhijian.entity.User;
import com.zzj.zhijian.service.OrderService;
import com.zzj.zhijian.service.UserService;
import com.zzj.zhijian.util.ResponseUtil;

/**
 * @author Administrator
 *
 */
public class StatisticsAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	@Resource
	private UserService userService;
	@Resource
	private OrderService orderService;
	private Double totalSales; // �����ܶ
	private Double lastYearTotalSales; // ����ȫ������(����)��
	private Double thisYearTotalSales; // ����ȫ������(����)��
	private Long tNumberOfOrder; // �ܶ�����(�ѳɽ�)��
	private Long tNumberOfMember; // �ܻ�Ա����
	private Long tNumberOfNoBuyMember; // δ�������Ʒ�Ļ�Ա����
	
	private Integer chartOption;
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
	public Integer getChartOption()
	{
		return chartOption;
	}
	public void setChartOption(Integer chartOption)
	{
		this.chartOption = chartOption;
	}
	/**
	 * ��ʼ����ҳͳ������ 
	 * 
	 * @throws Exception
	 */
	public void initMainStatistcs() throws Exception
	{
		//�����ܶ�--����
		totalSales = orderService.getTotalSales(1);
		//���������ܶ�
		lastYearTotalSales = orderService.getTotalSales(-1);
		//���������ܶ�--����
		thisYearTotalSales = orderService.getTotalSales(0);
		//�ܶ�����
		Order s_order = new Order();
		tNumberOfOrder = orderService.getOrderCount(s_order);
		//�ܻ�Ա��
		User s_user = new User();
		tNumberOfMember = userService.getUserCount(s_user);
		//δ�������Ʒ�Ļ�Ա��
		List<Order> orderList = new ArrayList<Order>();
		User user = new User();
		user.setOrderList(orderList);
		tNumberOfNoBuyMember = userService.getUserCount(user);
	}
	/**
	 * ˢ����ҳͳ������
	 * @throws Exception 
	 */
	public String getMainStatistcsInfo() throws Exception{
		this.initMainStatistcs();
		JSONObject result = new JSONObject();
		result.put("totalSales", totalSales);
		result.put("lastYearTotalSales", lastYearTotalSales);
		result.put("thisYearTotalSales", thisYearTotalSales);
		result.put("tNumberOfOrder", tNumberOfOrder);
		result.put("tNumberOfMember", tNumberOfMember);
		result.put("tNumberOfNoBuyMember", tNumberOfNoBuyMember);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	/**
	 * ˢ����ҳͼ����Ϣ
	 * @return
	 * @throws Exception
	 */
	public String getMainChart() throws Exception
	{
		System.out.println(">>>>>>>>>>>>>>chartOption:"+chartOption);
		List<DataCount> dataCount1 = orderService.getOrderCount(chartOption);
		List<DataCount> dataCount2 = userService.getUserCount(chartOption);
		for(DataCount oc:dataCount1){
			System.out.print(oc.getTime()+":"+oc.getCount()+" ");
		}
		System.out.println();
		for(DataCount oc:dataCount2){
			System.out.print(oc.getTime()+":"+oc.getCount()+" ");
		}
		JSONObject result = new JSONObject();
		result.put("orderCount", dataCount1);
		result.put("userCount", dataCount2);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String createExcel(){
		return null;
	}
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
		
	}

}
