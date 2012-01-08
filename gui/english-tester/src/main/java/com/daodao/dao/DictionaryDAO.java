package com.daodao.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.daodao.model.DictionaryDO;

public class DictionaryDAO extends AbstractDAO<Long, DictionaryDO> {

	@Override
	protected String getTableName() {
		return DictionaryDO.class.getName();
	}

	public List<DictionaryDO> copyWords(final Map<String, Object> param,
			final int startPos, final int pageSize) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<DictionaryDO>>() {
					@Override
					public List<DictionaryDO> doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuilder builder = new StringBuilder();
						builder.append("select model from " + getTableName());
						builder.append(" model where 1=1");
						if (param != null || param.size() > 0) {
							for (Entry<String, Object> entry : param.entrySet()) {
								if (entry.getKey().equals("accurate")) {
									builder.append(" and model."
											+ entry.getKey() + "<=:"
											+ entry.getKey());
								} else {
									builder.append(" and model."
											+ entry.getKey() + "=:"
											+ entry.getKey());
								}
							}
						}
						builder.append(" order by id");
						Query query = session.createQuery(builder.toString());
						query.setFirstResult(startPos).setFetchSize(pageSize)
								.setMaxResults(pageSize);
						if (param != null || param.size() > 0) {
							for (Entry<String, Object> entry : param.entrySet()) {
								query.setParameter(entry.getKey(),
										entry.getValue());
							}
						}
						return query.list();
					}
				});
	}

	public Map<String, Integer> getSourceCountMap() {
		return getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "select source, count(*) total from dictionary group by source";
				List<Object[]> temp = session.createSQLQuery(sql).list();
				Map<String, Integer> result = new HashMap<String, Integer>();
				for (int index = 0; index < temp.size(); index++) {
					result.put(temp.get(index)[0].toString(),
							(Integer) temp.get(index)[1]);
				}
				return result;
			}
		});
	}

	public Long getCountByFields(final Map<String, Object> fields) {
		return getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Long doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuilder builder = new StringBuilder();
				builder.append("select count(*) from " + getTableName()
						+ " model where 1=1 ");
				if (fields != null && fields.size() > 0) {
					for (String key : fields.keySet()) {
						if (key.endsWith("accurate")) {
							builder.append(" and model." + key + "<=:" + key);
						} else {
							builder.append(" and model." + key + "=:" + key);
						}
					}
				}
				Query query = session.createQuery(builder.toString());
				if (fields != null && fields.size() > 0) {
					for (Entry<String, Object> entry : fields.entrySet()) {
						query.setParameter(entry.getKey(), entry.getValue());
					}
				}
				return (Long) query.list().get(0);
			}
		});
	}

}
