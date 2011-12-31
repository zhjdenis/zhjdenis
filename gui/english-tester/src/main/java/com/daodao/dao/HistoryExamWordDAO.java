package com.daodao.dao;

import com.daodao.model.HistoryExamWordDO;

public class HistoryExamWordDAO extends AbstractDAO<Long, HistoryExamWordDO> {

	@Override
	protected String getTableName() {
		return HistoryExamWordDAO.class.getName();
	}

}
