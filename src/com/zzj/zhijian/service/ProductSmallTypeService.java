package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.bean.ProductSmallType;

/**
 * ��ƷС��service�ӿ�
 * 
 * @author zhaozengjie
 *
 */
public interface ProductSmallTypeService
{

	/**
	 * ��ҳ��ѯ��ƷС��
	 * 
	 * @param s_productSmallType
	 * @param pageBean
	 * @return
	 */
	public List<ProductSmallType> findProductSmallTypeList(
			ProductSmallType s_productSmallType, PageBean pageBean);

	/**
	 * �Ƿ����ָ����Ʒ�������ƷС��
	 * 
	 * @param bigTypeId
	 * @return
	 */
	public boolean existSmallTypeWithBigTypeId(int bigTypeId);

	/**
	 * ��ѯ��ƷС������
	 * 
	 * @param s_productSmallType
	 * @return
	 */
	public Long getProductSmallTypeCount(ProductSmallType s_productSmallType);

	/**
	 * ������ƷС��
	 * 
	 * @param productSmallType
	 */
	public void saveProductSmallType(ProductSmallType productSmallType);

	/**
	 * ɾ����ƷС��
	 * 
	 * @param productSmallType
	 */
	public void delete(ProductSmallType productSmallType);

	/**
	 * ͨ��id��ȡ��ƷС��ʵ��
	 * 
	 * @param id
	 * @return
	 */
	public ProductSmallType getProductSmallTypeById(int id);
}
