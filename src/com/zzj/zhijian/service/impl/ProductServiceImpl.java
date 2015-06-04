package com.zzj.zhijian.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzj.zhijian.dao.BaseDAO;
import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.entity.Product;
import com.zzj.zhijian.service.ProductService;
import com.zzj.zhijian.util.StringUtil;

/**
 * 商品Service类
 * 
 * @author zhaozengjie
 *
 */
@Service("productService")
public class ProductServiceImpl implements ProductService
{

	@Resource
	private BaseDAO<Product> baseDAO;

	/**
	 * 设计思路：对于查询所有的条件统一用and开始 hql拼接完成后，因为都是and开头 拼接条件查询语句
	 * 需要将第一个and替换为where生成正确的hql语句
	 */
	@Override
	public List<Product> findProductList(Product s_product, PageBean pageBean)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Product");
		if (s_product != null)
		{
			if (s_product.getBigType() != null)
			{
				hql.append(" and bigType.id=?");
				param.add(s_product.getBigType().getId());
			}
			if (s_product.getSmallType() != null)
			{
				hql.append(" and smallType.id=?");
				param.add(s_product.getSmallType().getId());
			}
			if (StringUtil.isNotEmpty(s_product.getName()))
			{
				hql.append(" and name like ?");
				param.add("%" + s_product.getName() + "%");
			}
			if (s_product.getSpecialPrice() == 1)
			{
				hql.append(" and specialPrice=1 order by specialPriceTime desc");
			}
			if (s_product.getHot() == 1)
			{
				hql.append(" and hot=1 order by hotTime desc");
			}
		}
		if (pageBean != null)
		{
			return baseDAO.find(hql.toString().replaceFirst("and", "where"),
					param, pageBean);
		} else
		{
			return null;
		}
	}

	@Override
	public Long getProductCount(Product s_product)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Product");
		if (s_product != null)
		{
			if (s_product.getBigType() != null)
			{
				hql.append(" and bigType.id=?");
				param.add(s_product.getBigType().getId());
			}
			if (s_product.getSmallType() != null)
			{
				hql.append(" and smallType.id=?");
				param.add(s_product.getSmallType().getId());
			}
			if (StringUtil.isNotEmpty(s_product.getName()))
			{
				hql.append(" and name like ?");
				param.add("%" + s_product.getName() + "%");
			}
		}
		return baseDAO
				.count(hql.toString().replaceFirst("and", "where"), param);
	}

	/**
	 * 设计思路：对于是使用id查询的对象,直接使用hibernate的session对象的get(class,serilizeble)方法
	 */
	@Override
	public Product getProductById(int productId)
	{
		return baseDAO.get(Product.class, productId);
	}

	/**
	 * 设计思路：对于对象持久化保存，使用merge来持久化新的或者合并session中id相同的对象然后持久化
	 */
	@Override
	public void saveProduct(Product product)
	{
		baseDAO.merge(product);
	}

	/**
	 * 设计思路:调用session对象的delete方法删除对象，内部机制实际上是通过id删除表中记录，
	 * 这一点可以通过hibernate打印的sql语句看出
	 */
	@Override
	public void deleteProduct(Product product)
	{
		baseDAO.delete(product);
	}

	@Override
	public void setProductWithHot(int productId)
	{
		Product product = baseDAO.get(Product.class, productId);
		product.setHot(1);
		product.setHotTime(new Date());
		baseDAO.save(product);
	}

	@Override
	public void setProductWithSpecialPrice(int productId)
	{
		Product product = baseDAO.get(Product.class, productId);
		product.setSpecialPrice(1);
		product.setSpecialPriceTime(new Date());
		baseDAO.save(product);
	}

	/**
	 * 非动态参数的直接构造符合语法的hql语句
	 */
	@Override
	public boolean existProductWithSmallTypeId(int smallTypeId)
	{
		String hql = "from Product where smallType.id=" + smallTypeId;
		if (baseDAO.find(hql.toString()).size() > 0)
		{
			return true;
		} else
		{
			return false;
		}
	}

}
