package com.daodao.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractDAO<K extends Serializable, V extends Serializable>
		extends HibernateDaoSupport {

	protected abstract String getTableName();

	public K saveEntity(V entity) {
		return (K) getHibernateTemplate().save(entity);
	}

	public List<K> batchSaveEntities(List<V> entities) {
		List<K> result = new ArrayList<K>();
		for (V entity : entities) {
			result.add(saveEntity(entity));
		}
		return result;
	}

	public List<V> findByIds(List<K> ids) {
		if (ids == null || ids.size() == 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("(-1");
		for (K id : ids) {
			builder.append(",");
			builder.append(id);
		}
		builder.append(")");
		String hql = "select model from " + getTableName()
				+ " model where id in " + builder.toString();
		return getHibernateTemplate().find(hql);
	}

	public V findById(K id) {
		if (id == null) {
			return null;
		}
		String hql = "select model from " + getTableName()
				+ " model where id = " + id;
		List<V> temp = getHibernateTemplate().find(hql);
		if (temp == null || temp.size() == 0) {
			return null;
		}
		return temp.get(0);
	}

}
