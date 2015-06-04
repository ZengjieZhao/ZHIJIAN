package com.zzj.zhijian.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ��Ʒʵ��
 * 
 * @author zhaozengjie
 *
 */
@Entity
@Table(name = "t_goods")
public class Product
{

	private int id; // ���
	private String name; // ��Ʒ����
	private int price; // �۸�
	private int stock; // ���
	private String proPic; // ��ƷͼƬ
	private String description; // ����
	private ProductSmallType smallType; // ����С��
	private ProductBigType bigType; // ��������
	private List<OrderItem> orderItemList = new ArrayList<OrderItem>(); // �м������
	private int hot; // �Ƿ�����
	private Date hotTime; // ��������ʱ��
	private int specialPrice; // �Ƿ��ؼ�
	private Date specialPriceTime; // �����ؼ�ʱ��

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

	@Column(length = 300)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public int getStock()
	{
		return stock;
	}

	public void setStock(int stock)
	{
		this.stock = stock;
	}

	public String getProPic()
	{
		return proPic;
	}

	public void setProPic(String proPic)
	{
		this.proPic = proPic;
	}

	@Column(length = 2000)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getHot()
	{
		return hot;
	}

	public void setHot(int hot)
	{
		this.hot = hot;
	}

	public Date getHotTime()
	{
		return hotTime;
	}

	public void setHotTime(Date hotTime)
	{
		this.hotTime = hotTime;
	}

	public int getSpecialPrice()
	{
		return specialPrice;
	}

	public void setSpecialPrice(int specialPrice)
	{
		this.specialPrice = specialPrice;
	}

	public Date getSpecialPriceTime()
	{
		return specialPriceTime;
	}

	public void setSpecialPriceTime(Date specialPriceTime)
	{
		this.specialPriceTime = specialPriceTime;
	}

	@ManyToOne
	@JoinColumn(name = "bigTypeId")
	public ProductBigType getBigType()
	{
		return bigType;
	}

	public void setBigType(ProductBigType bigType)
	{
		this.bigType = bigType;
	}

	@ManyToOne
	@JoinColumn(name = "smallTypeId")
	public ProductSmallType getSmallType()
	{
		return smallType;
	}

	public void setSmallType(ProductSmallType smallType)
	{
		this.smallType = smallType;
	}

	@OneToMany
	@JoinColumn(name = "productId")
	public List<OrderItem> getorderItemList()
	{
		return orderItemList;
	}

	public void setorderItemList(List<OrderItem> orderItemList)
	{
		this.orderItemList = orderItemList;
	}

}