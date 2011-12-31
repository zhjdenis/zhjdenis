package com.daodao.dao;

import com.daodao.model.ExamDO;

public class ExamDAO extends AbstractDAO<Long, ExamDO> {

	@Override
	protected String getTableName() {
		return ExamDAO.class.getName();
	}

}
