package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.bean.DataCount;
import com.zzj.zhijian.bean.Order;
import com.zzj.zhijian.bean.PageBean;

/**
 * 订单Service接口
 * 
 * @author zhaozengjie
 *
 */
public interface OrderService
{

	/**
	 * 保存订单
	 * 
	 * @param order
	 */
	public int saveOrder(Order order);

	/**
	 * 结算订单
	 * @param order
	 */
	public void balanceOrder(Order order,int id);
	/**
	 * 查询订单
	 * 
	 * @param order
	 * @return
	 */
	public List<Order> findOrder(Order s_order, PageBean pageBean);
	/**
	 * 查询订单无分页
	 * @param s_order
	 * @return
	 */
	public List<Order> findOrder(Order s_order);
	/**
	 * 获取订单的记录数
	 * 
	 * @param s_product
	 * @return
	 */
	public Long getOrderCount(Order s_order);
	
	/**
	 * 修改订单状态
	 * 
	 * @param status
	 */
	public void updateOrderStatus(int status, String orderNo);

	/**
	 * 通过订单号获取订单
	 * 
	 * @param orderId
	 * @return
	 */
	public Order getOrderById(int id);
	/**
	 * 通过订单号
	 * @param orderNo
	 * @return
	 */
	public Order getOrderByOrderNo(String orderNo);
	
	/**
	 * 获取订单的记录数(统计报表)
	 * 
	 * @param s_product
	 * @return
	 */
	public List<DataCount> getOrderCount(int n);
	/**
	 * 汇总订单销售总额（统计报表）
	 * @return
	 */
	public Double getTotalSales(int n);
	/**
	 * 汇总订单销售总利润（统计报表）
	 * @param n
	 * @return
	 */
	public Double getTotalProfit(int n);
	
}
