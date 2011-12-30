package com.daodao.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
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
				+ " model where model.id = " + id;
		List<V> temp = getHibernateTemplate().find(hql);
		if (temp == null || temp.size() == 0) {
			return null;
		}
		return temp.get(0);
	}

	public List<V> findByFields(Map<String, Object> fields) {
		StringBuilder builder = new StringBuilder();
		builder.append("select model from " + getTableName()
				+ " model where 1=1 ");
		if (fields != null && fields.size() > 0) {
			for (String key : fields.keySet()) {
				builder.append(" and model." + key + "=?");
			}
		}
		builder.append(" order by id");
		return getHibernateTemplate().find(builder.toString(), fields.values());
	}

	@SuppressWarnings("unchecked")
	public List<V> findByFields(final Map<String, Object> fields,
			final int startPos, final int pageSize) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<V>>() {
					@Override
					public List<V> doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuilder builder = new StringBuilder();
						builder.append("select model from " + getTableName()
								+ " model where 1=1 ");
						if (fields != null && fields.size() > 0) {
							for (String key : fields.keySet()) {
								builder.append(" and model." + key + "=?");
							}
						}
						builder.append(" order by id");
						Query query = session.createQuery(builder.toString());
						if (fields != null && fields.size() > 0) {
							for (Entry<String, Object> entry : fields
									.entrySet()) {
								query.setParameter(entry.getKey(),
										entry.getValue());
							}
						}
						query.setFirstResult(startPos).setFetchSize(pageSize)
								.setMaxResults(pageSize);
						return query.list();
					}
				});

	}

	public int updateFieldById(K id, Map<String, Object> fields) {
		if (fields == null || fields.size() == 0) {
			return 0;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("update " + getTableName() + " set id=id ");
		for (String key : fields.keySet()) {
			builder.append(" ," + key + "=?");
		}
		builder.append(" where id=" + id);

		return getHibernateTemplate().bulkUpdate(builder.toString(),
				fields.values());
	}

	public int deleteByIds(List<K> ids) {
		if (ids == null || ids.size() == 0) {
			return 0;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("delete from " + getTableName() + " where id in (-1");
		for (K id : ids) {
			builder.append("," + id);
		}
		builder.append(")");
		return getHibernateTemplate().bulkUpdate(builder.toString());
	}
}
