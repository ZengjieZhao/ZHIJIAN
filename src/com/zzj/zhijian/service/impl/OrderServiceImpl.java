package com.zzj.zhijian.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzj.zhijian.bean.DataCount;
import com.zzj.zhijian.bean.Order;
import com.zzj.zhijian.bean.OrderItem;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.dao.BaseDAO;
import com.zzj.zhijian.service.OrderService;
import com.zzj.zhijian.util.StringUtil;

/**
 * 订单Service实现类
 * 
 * @author zhaozengjie
 *
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Resource
	private BaseDAO<Order> baseDAO;

	@Override
	public int saveOrder(Order order)
	{
		// TODO Auto-generated method stub
		// 插入新订单
		int id = (Integer) baseDAO.save(order);
		return id;
		
	}

	@Override
	public List<Order> findOrder(Order s_order, PageBean pageBean)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Order");
		if (s_order != null)
		{
			if (s_order.getUser() != null && s_order.getUser().getId() != 0)
			{
				hql.append(" and user.id=?");
				param.add(s_order.getUser().getId());
			}
			if (s_order.getUser() != null
					&& StringUtil.isNotEmpty(s_order.getUser().getUserName()))
			{
				hql.append(" and user.userName like ?");
				param.add("%" + s_order.getUser().getUserName() + "%");
			}
			if (StringUtil.isNotEmpty(s_order.getOrderNo()))
			{
				hql.append(" and orderNo like ?");
				param.add("%" + s_order.getOrderNo() + "%");
			}
		}
		// 根据创建的时间来排序
		hql.append(" order by createTime desc");
		if (pageBean != null)
		{
			return baseDAO.find(hql.toString().replaceFirst("and", "where"),
					param, pageBean);
		} else
		{
			return baseDAO.find(hql.toString().replaceFirst("and", "where"),
					param);
		}
	}
	
	
	@Override
	public List<Order> findOrder(Order s_order)
	{
		String hql ="from Order";
		return baseDAO.find(hql);
	}

	@Override
	public void updateOrderStatus(int status, String orderNo)
	{
		// TODO Auto-generated method stub
		List<Object> param = new LinkedList<Object>();
		String hql = "update Order set status=? where orderNo=?";
		param.add(status);
		param.add(orderNo);
		baseDAO.executeHql(hql, param);
	}

	@Override
	public Long getOrderCount(Order s_order)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Order");
		if (s_order != null)
		{
			if (s_order.getUser() != null && s_order.getUser().getId() != 0)
			{
				hql.append(" and user.id=?");
				param.add(s_order.getUser().getId());
			}
			if (s_order.getUser() != null
					&& StringUtil.isNotEmpty(s_order.getUser().getUserName()))
			{
				hql.append(" and user.userName like ?");
				param.add("%" + s_order.getUser().getUserName() + "%");
			}
			if (StringUtil.isNotEmpty(s_order.getOrderNo()))
			{
				hql.append(" and orderNo like ?");
				param.add("%" + s_order.getOrderNo() + "%");
			}
		}
		return baseDAO
				.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public Order getOrderById(int id)
	{
		return baseDAO.get(Order.class, id);
	}
	public Order getOrderByOrderNo(String orderNo){
		String hql = "from Order where orderNo="+orderNo;
		
		Order o = (Order) baseDAO.executeCommonhqlQeury(hql).get(0);
		return o;
	}
	@Override
 	public Double getTotalSales(int n)
	{
		StringBuffer hql = new StringBuffer("select sum(cost) from Order");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		List<Object> param = new LinkedList<Object>();
		if(n == 1){
			return baseDAO.sum(hql.toString(), param);
		}
		if (n == 0)
		{
			param.add(year);
			hql.append(" and year(createTime)= ? ");
		} else if (n == -1)
		{
			year -= 1;
			param.add(year);
			hql.append(" and year(createTime)= ? ");
		}
		return baseDAO.sum(hql.toString().replaceFirst("and", "where"), param);
	}
	
	@Override
	public Double getTotalProfit(int n)
	{
		StringBuffer hql = new StringBuffer("select sum(p.price*oi.num-p.costprice*oi.num) from Order o ,OrderItem oi ,Product p where o.id = oi.order.id and p.id = oi.product.id ");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		List<Object> param = new LinkedList<Object>();
		if(n == 1 ){
			return baseDAO.sum(hql.toString(), param);
		}
		if(n == 0){
			param.add(year);
			hql.append(" and year(o.createTime)= ? ");
		}else if(n == -1){
			year -= 1;
			param.add(year);
			hql.append(" and year(o.createTime)= ? ");
		}
		return baseDAO.sum(hql.toString(), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataCount> getOrderCount(int n)
	{
		String hql = "";
		List<DataCount> dataCount = null;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		System.out.println("year:"+year+"month"+(month+1)+"day:"+day);
		if (n == 1)
		{
			// 当天每小时
			hql = "select new com.zzj.zhijian.bean.DataCount(hour(createTime) as time, count(1) as count)  from Order where year(createTime)="
					+ year
					+ " and month(createTime)="
					+ (month + 1)
					+ " and day(createTime)="
					+ day
					+ " group by hour(createTime)";
			dataCount = new ArrayList<DataCount>();
			System.out.println(">>>>>>>size:" + dataCount.size());
			for (int i = 0; i < 24; i++)
			{
				DataCount oc = new DataCount();
				oc.setTime(i);
				dataCount.add(oc);
			}
			List<DataCount> temp = new ArrayList<DataCount>();
			temp = (List<DataCount>) baseDAO.executeCommonhqlQeury(hql);
			System.out.println("tempsize：" + temp.size());
			for (int i = 0; i < 24; i++)
			{
				for (DataCount oc : temp)
				{
					if (oc.getTime() == i)
					{
						dataCount.get(i).setCount(oc.getCount());
					}
				}
			}
			for (int i = 0; i < 24; i++)
			{
				if (dataCount.get(i).getCount() == null)
				{
					dataCount.get(i).setCount(0L);
				}
			}
		} else if (n == 2)
		{
			// 每星期
			hql = "select new com.zzj.zhijian.bean.DataCount(hour(createTime) as time, count(1) as count)  from Order where year(createTime)="
					+ year + " group by hour(createTime)";

		} else if (n == 3)
		{
			// 当月每天
			hql = "select new com.zzj.zhijian.bean.DataCount(day(createTime) as time, count(1) as count)  from Order where year(createTime)="
					+ year
					+ " and month(createTime)="
					+ (month + 1)
					+ " group by day(createTime)";
			dataCount = new ArrayList<DataCount>(30);
			for (int i = 1; i < 31; i++)
			{
				DataCount oc = new DataCount();
				oc.setTime(i);
				dataCount.add(oc);
			}

			List<DataCount> temp = new ArrayList<DataCount>();
			temp = (List<DataCount>) baseDAO.executeCommonhqlQeury(hql);
			for (int i = 0; i < 30; i++)
			{
				for (DataCount oc : temp)
				{
					if (oc.getTime() == i + 1)
					{
						dataCount.get(i).setCount(oc.getCount());
					}
				}
			}
			for (int i = 0; i < 30; i++)
			{
				if (dataCount.get(i).getCount() == null)
				{
					dataCount.get(i).setCount(0L);
				}
			}
		} else if (n == 4)
		{
			// 当年每月
			hql = "select new com.zzj.zhijian.bean.DataCount(month(createTime) as time, count(1) as count)  from Order where year(createTime)="
					+ year + " group by month(createTime)";
			dataCount = new ArrayList<DataCount>(12);
			for (int i = 1; i < 13; i++)
			{
				DataCount oc = new DataCount();
				oc.setTime(i);
				dataCount.add(oc);
			}

			List<DataCount> temp = new ArrayList<DataCount>();
			temp = (List<DataCount>) baseDAO.executeCommonhqlQeury(hql);
			for (int i = 0; i < 12; i++)
			{
				for (DataCount oc : temp)
				{
					if (oc.getTime() == i + 1)
					{
						dataCount.get(i).setCount(oc.getCount());
					}
				}
			}
			for (int i = 0; i < 12; i++)
			{
				if (dataCount.get(i).getCount() == null)
				{
					dataCount.get(i).setCount(0L);
				}
			}
		}
		return dataCount;
	}

	@Override
	public void balanceOrder(Order order,int id)
	{
		String hql = "update Order o set o.status=1 where o.id="+id;
		List<Object> param = new LinkedList<Object>();
		baseDAO.executeHql(hql, param);
		// 更新订单中的商品库存减去已购买的数量
		List<OrderItem> list = order.getOrderItemList();
		for (OrderItem op : list)
		{
			List<Object> param2 = new LinkedList<Object>();
			int id1 = op.getProduct().getId();
			int num = op.getNum();
			param2.add(num);
			param2.add(id1);
			String hql2 = "update Product p set p.stock = p.stock-? where p.id = ?";
			baseDAO.executeHql(hql2, param2);
		}
	}

}
