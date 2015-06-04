package com.zzj.zhijian.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.entity.Product;
import com.zzj.zhijian.entity.ShoppingCart;
import com.zzj.zhijian.entity.ShoppingCartItem;
import com.zzj.zhijian.entity.User;
import com.zzj.zhijian.service.ProductService;
import com.zzj.zhijian.util.NavUtil;
import com.zzj.zhijian.util.ResponseUtil;

/**
 * 购物Action类
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class ShoppingAction extends ActionSupport implements
		ServletRequestAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private String mainPage;
	private String navCode;

	private int count;

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getMainPage()
	{
		return mainPage;
	}

	public void setMainPage(String mainPage)
	{
		this.mainPage = mainPage;
	}

	public String getNavCode()
	{
		return navCode;
	}

	public void setNavCode(String navCode)
	{
		this.navCode = navCode;
	}

	@Resource
	private ProductService productService;

	private int productId;

	public int getProductId()
	{
		return productId;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	/**
	 * 添加购物车商品类
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addShoppingCartItem() throws Exception
	{
		HttpSession session = request.getSession();
		Product product = productService.getProductById(productId);

		ShoppingCart shoppingCart = (ShoppingCart) session
				.getAttribute("shoppingCart");
		if (shoppingCart == null)
		{
			shoppingCart = new ShoppingCart();
			User currentUser = (User) session.getAttribute("currentUser");
			shoppingCart.setUserId(currentUser.getId());
		}
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart
				.getShoppingCartItems();

		boolean flag = true;
		for (ShoppingCartItem scI : shoppingCartItemList)
		{
			if (scI.getProduct().getId() == product.getId())
			{
				scI.setCount(scI.getCount() + 1);
				flag = false;
				break;
			}
		}

		ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

		if (flag)
		{
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}

		session.setAttribute("shoppingCart", shoppingCart);

		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);

		return null;
	}

	/**
	 * 直接购买商品
	 * 
	 * @return
	 * @throws Exception
	 */
	public String buy() throws Exception
	{
		HttpSession session = request.getSession();
		Product product = productService.getProductById(productId);

		ShoppingCart shoppingCart = (ShoppingCart) session
				.getAttribute("shoppingCart");
		if (shoppingCart == null)
		{
			shoppingCart = new ShoppingCart();
			User currentUser = (User) session.getAttribute("currentUser");
			shoppingCart.setUserId(currentUser.getId());
		}
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart
				.getShoppingCartItems();

		boolean flag = true;
		for (ShoppingCartItem scI : shoppingCartItemList)
		{
			if (scI.getProduct().getId() == product.getId())
			{
				scI.setCount(scI.getCount() + 1);
				flag = false;
				break;
			}
		}

		ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

		if (flag)
		{
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}

		session.setAttribute("shoppingCart", shoppingCart);

		mainPage = "shopping/shopping.jsp";
		navCode = NavUtil.genNavCode("购物车");
		return SUCCESS;
	}

	/**
	 * 修改购物车商品类
	 * 
	 * @throws Exception
	 */
	public String updateShoppingCartItem() throws Exception
	{
		HttpSession session = request.getSession();
		Product product = productService.getProductById(productId);
		ShoppingCart shoppingCart = (ShoppingCart) session
				.getAttribute("shoppingCart");
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart
				.getShoppingCartItems();
		for (ShoppingCartItem scI : shoppingCartItemList)
		{
			if (scI.getProduct().getId() == product.getId())
			{
				scI.setCount(count);
				break;
			}
		}

		session.setAttribute("shoppingCart", shoppingCart);

		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);

		return null;
	}

	/**
	 * 删除一条商品记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String removeShoppingCartItem() throws Exception
	{
		HttpSession session = request.getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session
				.getAttribute("shoppingCart");
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart
				.getShoppingCartItems();
		for (int i = 0; i < shoppingCartItemList.size(); i++)
		{
			if (productId == shoppingCartItemList.get(i).getProduct().getId())
			{
				shoppingCartItemList.remove(i);
				break;
			}
		}
		shoppingCart.setShoppingCartItems(shoppingCartItemList);
		session.setAttribute("shoppingCart", shoppingCart);
		return "list";
	}

	/**
	 * 显示购物车商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		mainPage = "shopping/shopping.jsp";
		navCode = NavUtil.genNavCode("购物车");
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
	}

}
