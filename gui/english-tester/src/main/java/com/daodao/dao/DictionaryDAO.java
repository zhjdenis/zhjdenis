package com.daodao.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.daodao.model.DictionaryDO;
import com.daodao.ui.VocabularyDialog.VocabularySearchOption;
import com.daodao.ui.VocabularyDialog.VocabularySearchOption.Sort;

public class DictionaryDAO extends AbstractDAO<Long, DictionaryDO> {

	@Override
	protected String getTableName() {
		return DictionaryDO.class.getName();
	}

	public List<DictionaryDO> findSimilarWords(
			final VocabularySearchOption option) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<DictionaryDO>>() {
					@Override
					public List<DictionaryDO> doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuilder builder = new StringBuilder();
						builder.append("select en,zh,accurate from dictionary where 1=1 ");
						if (option.word != null
								&& option.word.trim().length() > 0) {
							builder.append(" and en like '%" + option.word
									+ "%' or zh like '%" + option.word + "%'");
						}
						if (option.source != null && option.source.length() > 0) {
							builder.append(" and source='" + option.source
									+ "'");
						}
						if (option.sort.equals(Sort.ACCURATE)) {
							builder.append(" order by accurate,en");
						} else if (option.sort.equals(Sort.ALPHABET)) {
							builder.append(" order by en");
						} else {
							builder.append(" order by en");
						}
						if (option.startPos >= 0 && option.pageSize >= 0) {
							builder.append(" offset " + option.startPos
									+ " rows fetch next "
									+ (option.startPos + option.pageSize)
									+ " rows only");
						}
						SQLQuery query = session.createSQLQuery(builder
								.toString());
						List<Object[]> tmp = query.list();
						List<DictionaryDO> result = new ArrayList<>();
						for (Object[] entry : tmp) {
							DictionaryDO dictionaryDO = new DictionaryDO();
							dictionaryDO.setEn(entry[0].toString());
							dictionaryDO.setZh(entry[1].toString());
							dictionaryDO.setAccurate((Integer) entry[2]);
							result.add(dictionaryDO);
						}
						return result;
					}
				});
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
				String sql = "select source, count(*) total from dictionary where source is not null group by source";
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
