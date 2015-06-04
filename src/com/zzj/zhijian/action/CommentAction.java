package com.zzj.zhijian.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zzj.zhijian.entity.Comment;
import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.service.CommentService;
import com.zzj.zhijian.util.PageUtil;
import com.zzj.zhijian.util.ResponseUtil;
import com.zzj.zhijian.util.StringUtil;

/**
 * 留言Action类
 * 
 * @author zhaozengjie
 *
 */
@Controller
public class CommentAction extends ActionSupport implements ServletRequestAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	/**
	 * 留言Service
	 */
	@Resource
	private CommentService commentService;
	/**
	 * 评论列表
	 */
	private List<Comment> commentList;

	private String page;

	private String rows;

	private Long total;

	private String pageCode;

	private Comment s_Comment;

	private Comment comment;

	private int commentId;

	private String ids;

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public int getCommentId()
	{
		return commentId;
	}

	public void setCommentId(int commentId)
	{
		this.commentId = commentId;
	}

	public String getRows()
	{
		return rows;
	}

	public void setRows(String rows)
	{
		this.rows = rows;
	}

	public List<Comment> getCommentList()
	{
		return commentList;
	}

	public void setCommentList(List<Comment> commentList)
	{
		this.commentList = commentList;
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public Long getTotal()
	{
		return total;
	}

	public void setTotal(Long total)
	{
		this.total = total;
	}

	public String getPageCode()
	{
		return pageCode;
	}

	public void setPageCode(String pageCode)
	{
		this.pageCode = pageCode;
	}

	public Comment getS_Comment()
	{
		return s_Comment;
	}

	public void setS_Comment(Comment s_Comment)
	{
		this.s_Comment = s_Comment;
	}

	public Comment getComment()
	{
		return comment;
	}

	public void setComment(Comment comment)
	{
		this.comment = comment;
	}

	/**
	 * 留言查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		// 初次加载先判断分页是否为空，为空则从第一页开始
		if (StringUtil.isEmpty(page))
		{
			page = "1";
		}
		// 创建分页的bean置默认显记录数3条
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		// 评论列表接收数据库返回的数据
		commentList = commentService.findCommentList(s_Comment, pageBean);
		// 获取评论的总数
		total = commentService.getCommentCount(s_Comment);
		pageCode = PageUtil.genPaginationNoParam(request.getContextPath()
				+ "/comment_list.action", total, Integer.parseInt(page), 3);
		return SUCCESS;
	}

	/**
	 * 后台留言分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listComment() throws Exception
	{
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<Comment> commentList = commentService.findCommentList(s_Comment,
				pageBean);
		long total = commentService.getCommentCount(s_Comment);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(commentList, jsonConfig);
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 保存留言
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception
	{
		if (comment.getCreateTime() == null)
		{
			comment.setCreateTime(new Date());
		}
		commentService.saveComment(comment);
		return "save";
	}

	/**
	 * 通过id加载Comment实体
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadCommentById() throws Exception
	{
		Comment comment = commentService.getCommentById(commentId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject result = JSONObject.fromObject(comment, jsonConfig);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 回复留言
	 * 
	 * @return
	 * @throws Exception
	 */
	public String replay() throws Exception
	{
		comment.setReplyTime(new Date());
		commentService.saveComment(comment);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	/**
	 * 删除留言
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception
	{
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			Comment comment = commentService.getCommentById(Integer
					.parseInt(idsStr[i]));
			commentService.delete(comment);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
	}

}
