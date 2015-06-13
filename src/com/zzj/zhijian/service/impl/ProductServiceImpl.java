package com.zzj.zhijian.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.bean.Product;
import com.zzj.zhijian.dao.BaseDAO;
import com.zzj.zhijian.service.ProductService;
import com.zzj.zhijian.util.StringUtil;

/**
 * ��ƷService��
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
	 * ���˼·�����ڲ�ѯ���е�����ͳһ��and��ʼ hqlƴ����ɺ���Ϊ����and��ͷ ƴ��������ѯ���
	 * ��Ҫ����һ��and�滻Ϊwhere������ȷ��hql���
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
	 * ���˼·��������ʹ��id��ѯ�Ķ���,ֱ��ʹ��hibernate��session�����get(class,serilizeble)����
	 */
	@Override
	public Product getProductById(int productId)
	{
		return baseDAO.get(Product.class, productId);
	}

	/**
	 * ���˼·�����ڶ���־û����棬ʹ��merge���־û��µĻ��ߺϲ�session��id��ͬ�Ķ���Ȼ��־û�
	 */
	@Override
	public void saveProduct(Product product)
	{
		baseDAO.merge(product);
	}

	/**
	 * ���˼·:����session�����delete����ɾ�������ڲ�����ʵ������ͨ��idɾ�����м�¼��
	 * ��һ�����ͨ��hibernate��ӡ��sql��俴��
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
	 * �Ƕ�̬������ֱ�ӹ�������﷨��hql���
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
