package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.bean.DataCount;
import com.zzj.zhijian.bean.Order;
import com.zzj.zhijian.bean.PageBean;

/**
 * ����Service�ӿ�
 * 
 * @author zhaozengjie
 *
 */
public interface OrderService
{

	/**
	 * ���涩��
	 * 
	 * @param order
	 */
	public int saveOrder(Order order);

	/**
	 * ���㶩��
	 * @param order
	 */
	public void balanceOrder(Order order,int id);
	/**
	 * ��ѯ����
	 * 
	 * @param order
	 * @return
	 */
	public List<Order> findOrder(Order s_order, PageBean pageBean);
	/**
	 * ��ѯ�����޷�ҳ
	 * @param s_order
	 * @return
	 */
	public List<Order> findOrder(Order s_order);
	/**
	 * ��ȡ�����ļ�¼��
	 * 
	 * @param s_product
	 * @return
	 */
	public Long getOrderCount(Order s_order);
	
	/**
	 * �޸Ķ���״̬
	 * 
	 * @param status
	 */
	public void updateOrderStatus(int status, String orderNo);

	/**
	 * ͨ�������Ż�ȡ����
	 * 
	 * @param orderId
	 * @return
	 */
	public Order getOrderById(int id);
	/**
	 * ͨ��������
	 * @param orderNo
	 * @return
	 */
	public Order getOrderByOrderNo(String orderNo);
	
	/**
	 * ��ȡ�����ļ�¼��(ͳ�Ʊ���)
	 * 
	 * @param s_product
	 * @return
	 */
	public List<DataCount> getOrderCount(int n);
	/**
	 * ���ܶ��������ܶͳ�Ʊ���
	 * @return
	 */
	public Double getTotalSales(int n);
	/**
	 * ���ܶ�������������ͳ�Ʊ���
	 * @param n
	 * @return
	 */
	public Double getTotalProfit(int n);
	
}
