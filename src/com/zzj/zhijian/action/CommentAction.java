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
import com.zzj.zhijian.bean.Comment;
import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.service.CommentService;
import com.zzj.zhijian.util.PageUtil;
import com.zzj.zhijian.util.ResponseUtil;
import com.zzj.zhijian.util.StringUtil;

/**
 * ����Action��
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
	 * ����Service
	 */
	@Resource
	private CommentService commentService;
	/**
	 * �����б�
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
	private JSONObject responseJson ; 
	
	public JSONObject getResponseJson()
	{
		return responseJson;
	}

	public void setResponseJson(JSONObject responseJson)
	{
		this.responseJson = responseJson;
	}

	public CommentAction()
	{
		responseJson = new JSONObject();
	}

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
	 * ���Բ�ѯ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception
	{
		// ���μ������жϷ�ҳ�Ƿ�Ϊ�գ�Ϊ����ӵ�һҳ��ʼ
		if (StringUtil.isEmpty(page))
		{
			page = "1";
		}
		// ������ҳ��bean��Ĭ���Լ�¼��3��
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		// �����б�������ݿⷵ�ص�����
		commentList = commentService.findCommentList(s_Comment, pageBean);
		// ��ȡ���۵�����
		total = commentService.getCommentCount(s_Comment);
		pageCode = PageUtil.genPaginationNoParam(request.getContextPath()
				+ "/comment_list.action", total, Integer.parseInt(page), 3);
		return SUCCESS;
	}

	/**
	 * ��̨���Բ�ѯ
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
		responseJson.clear();
		responseJson.put("rows", rows);
		responseJson.put("total", total);
		return "json";
	}

	/**
	 * ��������
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
	 * ͨ��id����Comment
	 * @return
	 * @throws Exception
	 */
	public String loadCommentById() throws Exception
	{
		Comment comment = commentService.getCommentById(commentId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		responseJson.clear();
		responseJson = JSONObject.fromObject(comment, jsonConfig);
		return "json";
	}

	/**
	 * �ظ�����
	 * @return
	 * @throws Exception
	 */
	public String replay() throws Exception
	{
		comment.setReplyTime(new Date());
		commentService.saveComment(comment);
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	/**
	 * ɾ������
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception
	{
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++)
		{
			Comment comment = commentService.getCommentById(Integer
					.parseInt(idsStr[i]));
			commentService.delete(comment);
		}
		responseJson.clear();
		responseJson.put("success", true);
		return "json";
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
	}

}
