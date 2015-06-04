package com.zzj.zhijian.service;

import java.util.List;

import com.zzj.zhijian.entity.Notice;
import com.zzj.zhijian.entity.PageBean;

/**
 * ����Service��
 * 
 * @author zhaozengjie
 *
 */
public interface NoticeService
{

	/**
	 * ���ҹ��漯��
	 * 
	 * @return
	 */
	public List<Notice> findNoticeList(Notice s_notice, PageBean pageBean);

	/**
	 * ��ѯ��������
	 * 
	 * @param s_notice
	 * @return
	 */
	public Notice getNoticeById(int noticeId);

	/**
	 * ͨ������id��ȡ����
	 * 
	 * @param noticeId
	 * @return
	 */
	public Long getNoticeCount(Notice s_notice);

	/**
	 * ���湫��
	 * 
	 * @param notice
	 */
	public void saveNotice(Notice notice);

	/**
	 * ɾ������
	 * 
	 * @param notice
	 */
	public void delete(Notice notice);
}
