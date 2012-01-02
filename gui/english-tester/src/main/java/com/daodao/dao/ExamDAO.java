package com.daodao.dao;

import java.util.List;

import com.daodao.model.ExamDO;

public class ExamDAO extends AbstractDAO<Long, ExamDO> {

	@Override
	protected String getTableName() {
		return ExamDO.class.getName();
	}

	public List<ExamDO> listRemainExams() {
		return getHibernateTemplate().find(
				"select model from " + getTableName()
						+ " model where model.remain > 0");
	}
}
