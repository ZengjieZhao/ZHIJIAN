package com.zzj.zhijian.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 新闻实体
 * 
 * @author zhaozengjie
 *
 */
@Entity
@Table(name = "t_news")
public class News
{

	private int id; // 编号
	private String title; // 标题
	private String content; // 内容
	private Date createTime; // 创建时间

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

	@Column(length = 50)
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Lob 注解属性将被持久化为 （二进制大对象）Blog 或（大字符对象） Clob 类型。 在MySQL中没有Clob，用Text代替了，
	 * 而且分为了tinytext， text，mediumtext，longtext。Blob也按这种方式分成了四种。
	 * 
	 * @return
	 */
	@Lob
	@Column(columnDefinition = "TEXT")
	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * 时间默认
	 * 
	 * @return
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

}
