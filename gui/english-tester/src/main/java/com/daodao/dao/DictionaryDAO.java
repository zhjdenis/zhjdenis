package com.daodao.dao;

import java.sql.SQLException;
import java.util.List;

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

	public List<DictionaryDO> copyWords(final int startPos, final int pageSize) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<DictionaryDO>>() {
					@Override
					public List<DictionaryDO> doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuilder builder = new StringBuilder();
						builder.append("select model from " + getTableName());
						builder.append(" model order by id");
						Query query = session.createQuery(builder.toString());

						query.setFirstResult(startPos).setFetchSize(pageSize)
								.setMaxResults(pageSize);
						return query.list();
					}
				});
	}

}
