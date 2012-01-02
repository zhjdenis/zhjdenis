package com.daodao.dao;

import com.daodao.model.ExamWordDO;

public class ExamWordDAO extends AbstractDAO<Long, ExamWordDO> {

	@Override
	protected String getTableName() {
		return ExamWordDO.class.getName();
	}

}
