package com.zzj.zhijian.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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

/**
 * 主页初始化Action类
 * 
 * @author zhaozengjie
 *
 */
@Component
public class InitAction implements ServletContextListener,
		ApplicationContextAware
{

	private static ApplicationContext applicationContext;

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		// TODO Auto-generated method stub
		ServletContext application = servletContextEvent.getServletContext();
		ProductBigTypeService productBigTypeService = (ProductBigTypeService) applicationContext
				.getBean("productBigTypeService");
		List<ProductBigType> bigTypeList = productBigTypeService
				.findAllBigTypeList();
		application.setAttribute("bigTypeList", bigTypeList);

		TagService tagService = (TagService) applicationContext
				.getBean("tagService");
		List<Tag> tagList = tagService.findTagList(null, null);
		application.setAttribute("tagList", tagList);

		NoticeService noticeService = (NoticeService) applicationContext
				.getBean("noticeService");
		List<Notice> noticeList = noticeService.findNoticeList(null,
				new PageBean(1, 7));
		application.setAttribute("noticeList", noticeList);

		NewsService newsService = (NewsService) applicationContext
				.getBean("newsService");
		List<News> newsList = newsService
				.findNewsList(null, new PageBean(1, 7));
		application.setAttribute("newsList", newsList);

		ProductService productService = (ProductService) applicationContext
				.getBean("productService");
		Product s_product = new Product();
		s_product.setSpecialPrice(1);
		List<Product> specialPriceProductList = productService.findProductList(
				s_product, new PageBean(1, 10));
		application.setAttribute("specialPriceProductList",
				specialPriceProductList);

		s_product = new Product();
		s_product.setHot(1);
		List<Product> hotProductList = productService.findProductList(
				s_product, new PageBean(1, 7));
		application.setAttribute("hotProductList", hotProductList);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException
	{
		InitAction.applicationContext = applicationContext;
	}

}
