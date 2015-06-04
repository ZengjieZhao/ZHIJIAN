package com.zzj.zhijian.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车实体对象 非映射
 * 
 * @author zhaozengjie
 *
 */
public class ShoppingCart
{

	private int userId; // 购物车用户id
	private List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>(); // 购物车条目集合

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
