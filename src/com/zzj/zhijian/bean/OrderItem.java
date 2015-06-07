package com.zzj.zhijian.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 订单商品关联表
 * 
 * @author zhaozengjie
 *
 */
@Entity
@Table(name = "t_orderitem")
public class OrderItem
{

	private int id; // 主键
	private int num; // 商品数量
	private Order order; // 订单
	private Product product; // 商品

	@Id
	@GeneratedValue(generator = "_native")
	@GenericGenerator(name = "_native", strategy = "native")
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	/**
	 * ManyToOne默认fetch=FetchType.LAZY,
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name = "orderId")
	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	@ManyToOne
	@JoinColumn(name = "productId")
	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

}
