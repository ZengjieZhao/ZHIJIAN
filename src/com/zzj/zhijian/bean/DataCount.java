package com.zzj.zhijian.bean;

public class DataCount {
	private Integer time;
	private Long count;
	public DataCount(){
		
	}
	public DataCount(Integer time, Long count)
	{
		super();
		this.time = time;
		this.count = count;
	}
	public Integer getTime()
	{
		return time;
	}
	public void setTime(Integer time)
	{
		this.time = time;
	}
	public Long getCount()
	{
		return count;
	}
	public void setCount(Long count)
	{
		this.count = count;
	}
}
