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
import com.zzj.zhijian.bean.DataCount;
import com.zzj.zhijian.bean.Order;
import com.zzj.zhijian.bean.User;
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
	private Double totalProfit;//����������
	private Double lastYearTotalSales; // ����ȫ������(����)��
	private Double lastYearTotalProfit;
	private Double thisYearTotalSales; // ����ȫ������(����)��
	private Double thisYearTotalProfit;
	private Long tNumberOfOrder; // �ܶ�����(�ѳɽ�)��
	private Long tNumberOfMember; // �ܻ�Ա����
	private Long tNumberOfNoBuyMember; // δ�������Ʒ�Ļ�Ա����
	
	private Integer chartOption;
	
	private JSONObject responseJson ; 
	
	public JSONObject getResponseJson()
	{
		return responseJson;
	}
	public void setResponseJson(JSONObject responseJson)
	{
		this.responseJson = responseJson;
	}
	public StatisticsAction()
	{
		responseJson = new JSONObject();
	}
	public Double getTotalSales()
	{
		return totalSales;
	}
	public void setTotalSales(Double totalSales)
	{
		this.totalSales = totalSales;
	}
	
	public Double getTotalProfit()
	{
		return totalProfit;
	}
	public void setTotalProfit(Double totalProfit)
	{
		this.totalProfit = totalProfit;
	}
	public Double getLastYearTotalSales()
	{
		return lastYearTotalSales;
	}
	public void setLastYearTotalSales(Double lastYearTotalSales)
	{
		this.lastYearTotalSales = lastYearTotalSales;
	}
	
	public Double getLastYearTotalProfit()
	{
		return lastYearTotalProfit;
	}
	public void setLastYearTotalProfit(Double lastYearTotalProfit)
	{
		this.lastYearTotalProfit = lastYearTotalProfit;
	}
	public Double getThisYearTotalSales()
	{
		return thisYearTotalSales;
	}
	public void setThisYearTotalSales(Double thisYearTotalSales)
	{
		this.thisYearTotalSales = thisYearTotalSales;
	}
	
	public Double getThisYearTotalProfit()
	{
		return thisYearTotalProfit;
	}
	public void setThisYearTotalProfit(Double thisYearTotalProfit)
	{
		this.thisYearTotalProfit = thisYearTotalProfit;
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
		//����������--����
		totalProfit = orderService.getTotalProfit(1);
		//���������ܶ�
		lastYearTotalSales = orderService.getTotalSales(-1);
		//������������
		lastYearTotalProfit = orderService.getTotalProfit(-1);
		//���������ܶ�--����
		thisYearTotalSales = orderService.getTotalSales(0);
		//������������--����
		thisYearTotalProfit = orderService.getTotalProfit(0);
		//�ܶ�����
		Order s_order = new Order();
		tNumberOfOrder = orderService.getOrderCount(s_order);
		//�ܻ�Ա��
		User s_user = new User();
		s_user.setOrderList(null);
		tNumberOfMember = userService.getUserCount(s_user);
		//δ�������Ʒ�Ļ�Ա��
		User user = new User();
		tNumberOfNoBuyMember = userService.getUserCount(user);
	}
	/**
	 * ˢ����ҳͳ������
	 * @throws Exception 
	 */
	public String getMainStatistcsInfo() throws Exception{
		this.initMainStatistcs();
		responseJson.put("totalSales", totalSales);
		responseJson.put("totalProfit", totalProfit);
		responseJson.put("lastYearTotalSales", lastYearTotalSales);
		responseJson.put("lastYearTotalProfit", lastYearTotalProfit);
		responseJson.put("thisYearTotalSales", thisYearTotalSales);
		responseJson.put("thisYearTotalProfit", thisYearTotalProfit);
		responseJson.put("tNumberOfOrder", tNumberOfOrder);
		responseJson.put("tNumberOfMember", tNumberOfMember);
		responseJson.put("tNumberOfNoBuyMember", tNumberOfNoBuyMember);
		System.out.println(responseJson.toString());
		return "json";
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
		responseJson.clear();
		responseJson.put("orderCount", dataCount1);
		responseJson.put("userCount", dataCount2);
		return "json";
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
