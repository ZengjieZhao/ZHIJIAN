package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.entity.News;
import com.zzj.zhijian.entity.PageBean;

/**
 * 公告Service接口
 * 
 * @author zhaozengjie
 *
 */
public interface NewsService
{

	/**
	 * 查找新闻集合
	 * 
	 * @return
	 */
	public List<News> findNewsList(News s_news, PageBean pageBean);

	/**
	 * 通过新闻id获取新闻
	 * 
	 * @param newsId
	 * @return
	 */
	public News getNewsById(int newsId);

	/**
	 * 查询新闻数量
	 * 
	 * @param s_news
	 * @return
	 */
	public Long getNewsCount(News s_news);

	/**
	 * 保存新闻
	 * 
	 * @param news
	 */
	public void saveNews(News news);

	/**
	 * 删除新闻
	 * 
	 * @param news
	 */
	public void delete(News news);
}
