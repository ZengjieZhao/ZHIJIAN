package com.zzj.zhijian.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.entity.News;
import com.zzj.zhijian.entity.Notice;
import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.entity.Product;
import com.zzj.zhijian.entity.ProductBigType;
import com.zzj.zhijian.entity.Tag;
import com.zzj.zhijian.service.NewsService;
import com.zzj.zhijian.service.NoticeService;
import com.zzj.zhijian.service.ProductBigTypeService;
import com.zzj.zhijian.service.ProductService;
import com.zzj.zhijian.service.TagService;
import com.zzj.zhijian.util.ResponseUtil;

/**
 * ϵͳAction��
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class SysAction extends ActionSupport implements ApplicationAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> application;

	@Resource
	private ProductBigTypeService productBigTypeService;

	@Resource
	private TagService tagService;

	@Resource
	private NoticeService noticeService;

	@Resource
	private NewsService newsService;

	@Resource
	private ProductService productService;

	@Override
	public void setApplication(Map<String, Object> application)
	{
		// TODO Auto-generated method stub
		this.application = application;
	}

	/**
	 * ˢ��ϵͳ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String refreshSystem() throws Exception
	{
		List<ProductBigType> bigTypeList = productBigTypeService
				.findAllBigTypeList();
		application.put("bigTypeList", bigTypeList);

		List<Tag> tagList = tagService.findTagList(null, null);
		application.put("tagList", tagList);

		List<Notice> noticeList = noticeService.findNoticeList(null,
				new PageBean(1, 7));
		application.put("noticeList", noticeList);

		List<News> newsList = newsService
				.findNewsList(null, new PageBean(1, 7));
		application.put("newsList", newsList);

		Product s_product = new Product();
		s_product.setSpecialPrice(1);
		List<Product> specialPriceProductList = productService.findProductList(
				s_product, new PageBean(1, 8));
		application.put("specialPriceProductList", specialPriceProductList);

		s_product = new Product();
		s_product.setHot(1);
		List<Product> hotProductList = productService.findProductList(
				s_product, new PageBean(1, 6));
		application.put("hotProductList", hotProductList);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

}
