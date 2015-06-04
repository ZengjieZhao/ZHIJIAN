package com.zzj.zhijian.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzj.zhijian.dao.BaseDAO;
import com.zzj.zhijian.entity.Comment;
import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.service.CommentService;
import com.zzj.zhijian.util.StringUtil;

/**
 * ����Service��
 * 
 * @author zhaozengjie
 *
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService
{

	@Resource
	private BaseDAO<Comment> baseDAO;

	@Override
	public List<Comment> findCommentList(Comment s_Comment, PageBean pageBean)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Comment");
		if (s_Comment != null)
		{
			if (StringUtil.isNotEmpty(s_Comment.getContent()))
			{
				hql.append(" and content like ?");
				param.add("%" + s_Comment.getContent() + "%");
			}
		}
		hql.append(" order by createTime desc ");
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
	public Long getCommentCount(Comment s_Comment)
	{
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Comment");
		if (s_Comment != null)
		{
			if (StringUtil.isNotEmpty(s_Comment.getContent()))
			{
				hql.append(" and content like ?");
				param.add("%" + s_Comment.getContent() + "%");
			}
		}
		return baseDAO
				.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void saveComment(Comment comment)
	{
		baseDAO.merge(comment);
	}

	@Override
	public Comment getCommentById(int commentId)
	{
		return baseDAO.get(Comment.class, commentId);
	}

	@Override
	public void delete(Comment comment)
	{
		baseDAO.delete(comment);
	}

}