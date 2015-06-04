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
 * ����ʵ��
 * 
 * @author zhaozengjie
 *
 */
@Entity
@Table(name = "t_news")
public class News
{

	private int id; // ���
	private String title; // ����
	private String content; // ����
	private Date createTime; // ����ʱ��

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
	 * Lob ע�����Խ����־û�Ϊ �������ƴ����Blog �򣨴��ַ����� Clob ���͡� ��MySQL��û��Clob����Text�����ˣ�
	 * ���ҷ�Ϊ��tinytext�� text��mediumtext��longtext��BlobҲ�����ַ�ʽ�ֳ������֡�
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
	 * ʱ��Ĭ��
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
