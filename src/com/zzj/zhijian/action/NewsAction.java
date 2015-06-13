package com.zzj.zhijian.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.bean.News;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.service.NewsService;

/**
 * 新闻Action类
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class NewsAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private NewsService newsService;

	private News news;
	private int newsId;
	private String mainPage;
	private String navCode;

	private String page;
	private String rows;
	private News s_news;
	private String ids;
	private JSONObject responseJson ; 
	
	
	public JSONObject getResponseJson()
	{
		return responseJson;
	}

	public void setResponseJson(JSONObject responseJson)
	{
		this.responseJson = responseJson;
	}

	public NewsAction()
	{
		responseJson = new JSONObject();
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getRows()
	{
		return rows;
	}

	public void setRows(String rows)
	{
		this.rows = rows;
	}

	public News getS_news()
	{
		return s_news;
	}

	public void setS_news(News s_news)
	{
		this.s_news = s_news;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public News getNews()
	{
		return news;
	}

	public void setNews(News news)
	{
		this.news = news;
	}

	public int getNewsId()
	{
		return newsId;
	}

	public void setNewsId(int newsId)
	{
		this.newsId = newsId;
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

	/**
	 * 显示新闻详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showNews() throws Exception
	{
		news = newsService.getNewsById(newsId);
		mainPage = "news/newsDetails.jsp";
		navCode = "新闻信息";
		return SUCCESS;
	}

	/**
	 * 分页查询新闻信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<News> newsList = newsService.findNewsList(s_news, pageBean);
		long total = newsService.getNewsCount(s_news);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(newsList, jsonConfig);
		responseJson.clear();
		responseJson.put("rows", rows);
		responseJson.put("total", total);
		return "json";
	}

	/**
	 * 后台-保存新闻信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		if (news.getId() == 0)
		{
			news.setCreateTime(new Date());
		}
		newsService.saveNews(news);
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * 删除新闻
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception
	{
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			News news = newsService.getNewsById(Integer.parseInt(idsStr[i]));
			newsService.delete(news);
		}
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

}
