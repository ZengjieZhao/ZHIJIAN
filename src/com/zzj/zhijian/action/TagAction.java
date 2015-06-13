package com.zzj.zhijian.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.bean.Tag;
import com.zzj.zhijian.service.TagService;
import com.zzj.zhijian.util.ResponseUtil;

/**
 * 标签Action类
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class TagAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private TagService tagService;

	private Tag tag;
	private int tagId;
	private String mainPage;
	private String navCode;

	private String page;
	private String rows;
	private Tag s_tag;
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

	public TagAction()
	{
		responseJson = new JSONObject();
	}

	public Tag getTag()
	{
		return tag;
	}

	public void setTag(Tag tag)
	{
		this.tag = tag;
	}

	public int getTagId()
	{
		return tagId;
	}

	public void setTagId(int tagId)
	{
		this.tagId = tagId;
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

	public Tag getS_tag()
	{
		return s_tag;
	}

	public void setS_tag(Tag s_tag)
	{
		this.s_tag = s_tag;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	/**
	 * 分页查询标签信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<Tag> tagList = tagService.findTagList(s_tag, pageBean);
		long total = tagService.getTagCount(s_tag);
		JSONArray rows = JSONArray.fromObject(tagList);
		responseJson.clear();
		responseJson.put("rows", rows);
		responseJson.put("total", total);
		return "json";
	}

	/**
	 * 后台-保存标签信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		tagService.saveTag(tag);
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * 删除标签
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception
	{
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			Tag tag = tagService.getTagById(Integer.parseInt(idsStr[i]));
			tagService.delete(tag);
		}
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

}
