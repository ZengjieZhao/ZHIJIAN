package com.zzj.zhijian.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zzj.zhijian.bean.PageBean;
import com.zzj.zhijian.dao.BaseDAO;

@Repository("baseDAO")
@SuppressWarnings("all")
public class BaseDAOImpl<T> implements BaseDAO<T>
{

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	// 自动注入session工厂
	/**
	 * Autowired是根据类型进行自动装配的，如果需要按名称进行装配，则需要配合Qualifier
	 * Autowired标注可以放在成员变量上，也可以放在成员变量的set方法上。
	 * 前者，Spring会直接将UserDao类型的唯一一个bean赋值给userDao这个成员变量；
	 * 后者，Spring会调用setUserDao方法来将UserDao类型的唯一一个bean装配到userDao这个属性。
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	public Serializable save(T o)
	{
		return this.getCurrentSession().save(o);
	}

	public void delete(T o)
	{
		this.getCurrentSession().delete(o);
	}

	public void update(T o)
	{
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o)
	{
		this.getCurrentSession().saveOrUpdate(o);
	}

	public List<T> find(String hql)
	{
		return this.getCurrentSession().createQuery(hql).list();
	}

	public List<T> find(String hql, Object[] param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public List<T> find(String hql, List<Object> param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0)
		{
			for (int i = 0; i < param.size(); i++)
			{
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}

	public List<T> find(String hql, Object[] param, PageBean pageBean)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	public List<T> find(String hql, List<Object> param, PageBean pageBean)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0)
		{
			for (int i = 0; i < param.size(); i++)
			{
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	public T get(Class<T> c, Serializable id)
	{
		return (T) this.getCurrentSession().get(c, id);
	}

	public T get(String hql, Object[] param)
	{
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0)
		{
			return l.get(0);
		} else
		{
			return null;
		}
	}

	public T get(String hql, List<Object> param)
	{
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0)
		{
			return l.get(0);
		} else
		{
			return null;
		}
	}

	public Long count(String hql)
	{
		return (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	public Long count(String hql, Object[] param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, List<Object> param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0)
		{
			for (int i = 0; i < param.size(); i++)
			{
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	public Integer executeHql(String hql)
	{
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public Integer executeHql(String hql, Object[] param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, List<Object> param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0)
		{
			for (int i = 0; i < param.size(); i++)
			{
				q.setParameter(i, param.get(i));
			}
		}
		return q.executeUpdate();
	}

	public void merge(T o)
	{
		this.getCurrentSession().merge(o);
	}

	public Integer executeSql(String sql)
	{
		Query q = this.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public Double sum(String hql,List<Object> param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0)
		{
			for (int i = 0; i < param.size(); i++)
			{
				q.setParameter(i, param.get(i));
			}
		}
		return (Double)q.uniqueResult();
	}

	@Override
	public List executeCommonhqlQeury(String hql,List<Object> param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0)
		{
			for (int i = 0; i < param.size(); i++)
			{
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}

	@Override
	public List executeCommonhqlQeury(String hql)
	{
		return this.getCurrentSession().createQuery(hql).list();
	}

}
