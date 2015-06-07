package com.zzj.zhijian.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 留言实体
 * 
 * @author zhaozengjie
 *
 */
@Entity
@Table(name = "t_comment")
public class Comment
{

	private int id; // 留言编号
	private String content; // 留言内容
	private String nickName; // 网友昵称
	private Date createTime; // 创建时间
	private String replyContent; // 回复内容
	private Date replyTime; // 回复时间

	/**
	 * 注解id表明对应表中id主键字段, GeneratedValue主键生成策略native就是将主键的生成工作交由数据库完成，hibernate不管
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue(generator = "_native")
	@GenericGenerator(name = "_native", strategy = "native")
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Column(length = 1000)
	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@Column(length = 30)
	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	@Column(length = 100)
	public String getReplyContent()
	{
		return replyContent;
	}

	public void setReplyContent(String replyContent)
	{
		this.replyContent = replyContent;
	}

	public Date getReplyTime()
	{
		return replyTime;
	}

	public void setReplyTime(Date replyTime)
	{
		this.replyTime = replyTime;
	}

}
