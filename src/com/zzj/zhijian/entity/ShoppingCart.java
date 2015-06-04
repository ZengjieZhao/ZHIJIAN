package com.zzj.zhijian.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ﳵʵ����� ��ӳ��
 * 
 * @author zhaozengjie
 *
 */
public class ShoppingCart
{

	private int userId; // ���ﳵ�û�id
	private List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>(); // ���ﳵ��Ŀ����

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public List<ShoppingCartItem> getShoppingCartItems()
	{
		return shoppingCartItems;
	}

	public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems)
	{
		this.shoppingCartItems = shoppingCartItems;
	}

}