package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.entity.PageBean;
import com.zzj.zhijian.entity.Tag;

/**
 * ��ǩservice�ӿ�
 * 
 * @author zhaozengjie
 *
 */
public interface TagService
{

	/**
	 * ��ѯ��ǩ����
	 * 
	 * @return
	 */
	public List<Tag> findTagList(Tag s_tag, PageBean pageBean);

	/**
	 * ��ѯ��ǩ����
	 * 
	 * @param s_tag
	 * @return
	 */
	public Long getTagCount(Tag s_tag);

	/**
	 * �����ǩ
	 * 
	 * @param tag
	 */
	public void saveTag(Tag tag);

	/**
	 * ɾ����ǩ
	 * 
	 * @param tag
	 */
	public void delete(Tag tag);

	/**
	 * ͨ����ǩid��ȡ��ǩ
	 * 
	 * @param tagId
	 * @return
	 */
	public Tag getTagById(int tagId);
}
