package com.zzj.zhijian.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

/**
 * 订单实体
 * 
 * @author zhaozengjie
 *
 */
@Entity
@Table(name = "t_order")
public class Order
{

	private int id; // 编号
	private String orderNo; // 订单号
	private User user; // 用户
	private Date createTime; // 创建时间
	private float cost; // 总金额
	private int status; // 状态 1 待审核 2 审核通过 3 卖家已发货 4 已收获

	private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

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

	/**
	 * 默认的字符串字段
	 * 
	 * @return
	 */
	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public float getCost()
	{
		return cost;
	}

	public void setCost(float cost)
	{
		this.cost = cost;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * 注解多对一的关系
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name = "userId", updatable = false)
	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * 遇到的异常： org.hibernate.LazyInitializationException: failed to lazily
	 * initialize a collection of role:
	 * com.ptn.uim.bean.UimServFileRecord.uimServFileDatas, no session or
	 * session was closed 错误原因：
	 * 原因是在hibernate映射关系中由于延迟加载，session在调用前已经被关闭，，所以加载set属性时无可用session 解决方案：
	 * 在ManyToOne端设置fetch=FetchType.LAZY,OneToMany端设置fetch=FetchType.EAGER， 非懒加载
	 * 
	 * @return
	 */
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "orderId")
	public List<OrderItem> getOrderItemList()
	{
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList)
	{
		this.orderItemList = orderItemList;
	}

}
