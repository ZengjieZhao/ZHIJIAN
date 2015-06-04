package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.entity.DataCount;
import com.zzj.zhijian.entity.Order;
import com.zzj.zhijian.entity.PageBean;

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
	public void saveOrder(Order order);

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
	 * ��ȡ�����ļ�¼��(ͳ�Ʊ���)
	 * 
	 * @param s_product
	 * @return
	 */
	public List<DataCount> getOrderCount(int n);
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
	 * ���ܶ��������ܶͳ�Ʊ���
	 * @return
	 */
	public Double getTotalSales(int n);
	
}
