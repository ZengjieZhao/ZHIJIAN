package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.bean.News;
import com.zzj.zhijian.bean.PageBean;

/**
 * ����Service�ӿ�
 * 
 * @author zhaozengjie
 *
 */
public interface NewsService
{

	/**
	 * �������ż���
	 * 
	 * @return
	 */
	public List<News> findNewsList(News s_news, PageBean pageBean);

	/**
	 * ͨ������id��ȡ����
	 * 
	 * @param newsId
	 * @return
	 */
	public News getNewsById(int newsId);

	/**
	 * ��ѯ��������
	 * 
	 * @param s_news
	 * @return
	 */
	public Long getNewsCount(News s_news);

	/**
	 * ��������
	 * 
	 * @param news
	 */
	public void saveNews(News news);

	/**
	 * ɾ������
	 * 
	 * @param news
	 */
	public void delete(News news);
}
